package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentCreateRequestDto;
import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
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

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    //댓글 작성
    @Transactional
    public void createComment(CommentCreateRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(commentRequestDto.getPostId());
        if (!foundPost.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        Optional<User> foundAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (!foundAuthor.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        Post post = foundPost.get();
        User user = foundAuthor.get();
        Comment comment = new Comment(post, commentRequestDto.getContent(), user);
        commentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public void editComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Optional<User> foundUser = userRepository.findByUsername(userDetails.getUsername());
        if (!foundUser.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        Optional<Comment> foundComment = commentRepository.findCommentById(id);
        if (!foundComment.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }
        User user = foundUser.get();
        Comment comment = foundComment.get();

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
        Optional<User> foundUser = userRepository.findByUsername(userDetails.getUsername());
        if(!foundUser.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        Optional<Comment> foundComment = commentRepository.findCommentById(id);
        if (!foundComment.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }
        User user = foundUser.get();
        Comment comment = foundComment.get();

        if (!comment.isAvailable()) {
            throw new CustomException(COMMENT_IS_DELETED);
        }

        if(user.getUsername().equals(comment.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.delete();
            List<Heart> hearts = comment.getHearts();
            for (Heart heart : hearts) {
                heart.dislike();
                //heartRepository.save(heart);
            }
            commentRepository.save(comment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    @Transactional
    public List<Comment> findComments(Long id) {
        // 삭제 처리가 되지 않은 댓글만 찾아 반환한다.
        return commentRepository.findAllByPostIdAndAvailableTrue(id);
    }
}
