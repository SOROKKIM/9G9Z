package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentCreateRequestDto;
import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    //댓글 작성
    @Transactional
    public void createComment(CommentCreateRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postService.findPost(commentRequestDto.getPostId());
        User user = userService.findUser(userDetails.getUsername());
        Comment comment = new Comment(commentRequestDto.getContent(), user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public void editComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = userService.findUser(userDetails.getUsername());
        Comment comment = findComment(id);

        // 댓글 수정은 작성자 본인만 수행할 수 있다.
        if (user.getUsername().equals(comment.getUser().getUsername())) {
            comment.edit(commentRequestDto.getContent());
            commentRepository.save(comment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(long id, UserDetailsImpl userDetails) {
        User user = userService.findUser(userDetails.getUsername());
        Comment comment = findComment(id);

        // 댓글 삭제는 작성자 본인과 관리자만 수행할 수 있다
        if (user.getUsername().equals(comment.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.delete();

            // 연관된 모든 좋아요도 삭제 처리 한다.
            List<Heart> hearts = comment.getHearts();
            for (Heart heart : hearts) {
                heart.dislike();
            }

            commentRepository.save(comment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    @Transactional
    public List<Comment> findCommentsByPostId(Long postId) {
        // 삭제 처리가 되지 않은 댓글만 찾아 반환한다.
        return commentRepository.findAllByPostIdAndAvailableTrue(postId);
    }

    public Comment findComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        }

        Comment comment = optionalComment.get();
        if (!comment.isAvailable()) {
            throw new CustomException(COMMENT_IS_DELETED);
        }

        return comment;
    }
}
