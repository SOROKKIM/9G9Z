package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.HeartRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
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

    public ResponseEntity<ServerResponse> updatePostLikes(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Post post = postRepository.findPostById(postId).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );

        Optional<Heart> optionalHeart = heartRepository.findHeartByUserAndPost(user, post);
        if(optionalHeart.isPresent()) {
            Heart heart = optionalHeart.get();
            if (heart.isAvailable()) {
                heart.dislike();
                heartRepository.save(heart);
                return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
            } else {
                heart.like();
                heartRepository.save(heart);
                return ServerResponse.toResponseEntity(SUCCESS_LIKE);
            }
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

        Optional<Heart> optionalHeart = heartRepository.findHeartByUserAndComment(user, comment);
        if(optionalHeart.isPresent()) {
            Heart heart = optionalHeart.get();
            if (heart.isAvailable()) {
                heart.dislike();
                heartRepository.save(heart);
                return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
            } else {
                heart.like();
                heartRepository.save(heart);
                return ServerResponse.toResponseEntity(SUCCESS_LIKE);
            }
        } else {
            heartRepository.save(new Heart(user, comment));
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }

    public Integer getPostHeartCount(Post post) {
        return heartRepository.countByPost(post);
    }
}
