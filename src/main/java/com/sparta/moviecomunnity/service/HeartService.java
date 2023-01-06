package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
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
    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;
    private final RecommentService recommentService;

    public ResponseEntity<ServerResponse> updatePostLikes(Long postId, String username) {
        User user = userService.findUser(username);
        Post post = postService.findPost(postId);

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

    public ResponseEntity<ServerResponse> updateCommentLikes(Long commentId, String username) {
        User user = userService.findUser(username);
        Comment comment = commentService.findComment(commentId);

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

    public ResponseEntity<ServerResponse> updateRecommentLikes(Long id, User user) {
        Recomment recomment = recommentService.getRecomment(id);

        Optional<Heart> heart = heartRepository.findHeartByUserAndRecomment(user, recomment);
        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
            return ServerResponse.toResponseEntity(SUCCESS_DELETE_LIKE);
        }
        else {
            Heart newHeart = new Heart(user);
            newHeart.setRecomment(recomment);
            heartRepository.save(newHeart);
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

    public Integer getPostHeartCount(Post post) {
        return heartRepository.countByPostAndAvailableTrue(post);
    }

    public Integer getCommentHeartCount(Comment comment) {
        return heartRepository.countByCommentAndAvailableTrue(comment);
    }
}
