package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    //댓글 작성
    @Transactional
    public void createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(commentRequestDto.getPostId());
        if (!foundPost.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
            //Comment comment = new Comment(post, commentRequestDto.getCommentContent(), user);
            //commentRepository.saveAndFlush(comment);
        }

        Optional<User> foundAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (!foundAuthor.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        Post post = foundPost.get();
        User user = foundAuthor.get();


    }

    //댓글 수정
    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto) {
        Optional<Comment> foundComment = commentRepository.findCommentById(id);
        if (foundComment.isPresent()) {
            Comment comment = foundComment.get();
            comment.update(commentRequestDto.getCommentContent());
            commentRepository.saveAndFlush(comment);
        } else {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(long id) {
        Optional<Comment> foundComment = commentRepository.findCommentById(id);
        if (foundComment.isPresent()) {
//            Comment comment = foundComment.get();
//            commentRepository.delete(comment);
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
    }
}
