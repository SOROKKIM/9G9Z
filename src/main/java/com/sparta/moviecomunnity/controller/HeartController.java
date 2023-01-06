package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.CommentService;
import com.sparta.moviecomunnity.service.HeartService;

import com.sparta.moviecomunnity.service.PostService;
import com.sparta.moviecomunnity.service.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService likeService;
    private final PostService postService;
    private final CommentService commentService;
    private final RecommentService recommentService;

    @PutMapping("/posts/{id}/likes")
    public ResponseEntity<ServerResponse> likesPosts(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updatePostLikes(postService.findPost(id), userDetails.getUser());
    }

    @PutMapping("/comments/{id}/likes")
    public ResponseEntity<ServerResponse> likesComments(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updateCommentLikes(commentService.findComment(id), userDetails.getUser());
    }
    @PutMapping("/recomments/{id}/likes")
    public ResponseEntity<ServerResponse> likesRecomments(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updateRecommentLikes(recommentService.findRecomment(id), userDetails.getUser());
    }

}