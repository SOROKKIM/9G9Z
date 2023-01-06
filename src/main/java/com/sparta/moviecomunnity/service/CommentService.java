package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    //댓글 작성
    @Transactional
    public void createComment(String content, Post post, User user) {
        Comment comment = new Comment(content, user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public void editComment(Long id, String content, String username) {
        Comment comment = findComment(id);

        // 댓글 수정은 작성자 본인만 수행할 수 있다.
        if (username.equals(comment.getUser().getUsername())) {
            comment.edit(content);
            commentRepository.save(comment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long id, String username, UserRoleEnum role) {
        Comment comment = findComment(id);

        // 댓글 삭제는 작성자 본인과 관리자만 수행할 수 있다
        if (username.equals(comment.getUser().getUsername()) || role.equals(UserRoleEnum.ADMIN)) {
            comment.delete();

            // 연관된 모든 리코멘트도 삭제 처리 한다.
            List<Recomment> recomments = comment.getRecomments();
            for (Recomment recomment : recomments) {
                recomment.delete();
            }

            commentRepository.save(comment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByPostIdAndAvailableTrue(postId);
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        // 삭제 처리가 되지 않은 댓글만 찾아 반환한다.
        return commentResponseDtoList;
    }

    @Transactional(readOnly = true)
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
