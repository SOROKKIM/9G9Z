package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.HeartRepository;
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
    private final HeartRepository heartRepository;


    public ResponseEntity<ServerResponse> updatePostLikes(Post post, User user) {
        Optional<Heart> optionalHeart = heartRepository.findHeartByUserAndPost(user, post);
        if(optionalHeart.isPresent()) {
            return likeOrDislike(optionalHeart.get());
        }
        else {
            Heart heart = new Heart(user);
            heart.setPost(post);
            heartRepository.save(heart);
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }

    public ResponseEntity<ServerResponse> updateCommentLikes(Comment comment, User user) {

        Optional<Heart> optionalHeart = heartRepository.findHeartByUserAndComment(user, comment);
        if(optionalHeart.isPresent()) {
            return likeOrDislike(optionalHeart.get());
        } else {
            Heart heart = new Heart(user);
            heart.setComment(comment);
            heartRepository.save(heart);
            return ServerResponse.toResponseEntity(SUCCESS_LIKE);
        }
    }

    private ResponseEntity<ServerResponse> likeOrDislike(Heart heart) {
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

    public Integer getPostHeartCount(Long postId) {
        return heartRepository.countByPostIdAndAvailableTrue(postId);
    }

    public Integer getCommentHeartCount(Long commentId) {
        return heartRepository.countByCommentIdAndAvailableTrue(commentId);
    }
}
