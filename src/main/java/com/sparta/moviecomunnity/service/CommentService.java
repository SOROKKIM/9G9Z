package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.dto.HttpResponseDto;
import com.sparta.moviecomunnity.dto.ResponseDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    //댓글 작성
    @Transactional
    public void createComment(CommentRequestDto commentRequestDto, User user) {

//        Long id = commentRequestDto.getPostId();
//        postRepository.findPostById(id);
        Optional<Post> foundPost = postRepository.findPostById(commentRequestDto.getPostId());
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            Comment comment = new Comment(post, commentRequestDto.getCommentContent(), user);
            commentRepository.saveAndFlush(comment);
        }
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
    public HttpResponseDto deleteComment(long id) {
        Optional<Comment> foundComment = commentRepository.findCommentById(id);
        if (foundComment.isPresent()) {
            Comment comment = foundComment.get();
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
    }
}


