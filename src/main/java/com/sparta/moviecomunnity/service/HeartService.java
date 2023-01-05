package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ResponseCode;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@Transactional
@Service
@RequiredArgsConstructor
public class HeartService {
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final RecommentRepository recommentRepository;

    public ResponseEntity<ServerResponse> updatePostLikes(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Post post = postRepository.findPostById(postId).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndPost(user, post);
        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
            return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
        }
        else {
            heartRepository.save(new Heart(user, post));
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }

    public ResponseEntity<ServerResponse> updateCommentLikes(Long commentId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndComment(user, comment);
        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
            return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
        }
        else {
            heartRepository.save(new Heart(user, comment));
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }

    public ResponseEntity<ServerResponse> updateRecommentLikes(Long id, User user) {
        Recomment recomment = recommentRepository.findById(id).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndRecomment(user, recomment);
        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
            return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
        }
        else {
            heartRepository.save(new Heart(user, recomment));
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }
}
