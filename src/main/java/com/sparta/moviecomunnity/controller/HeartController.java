package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.HeartService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.sparta.moviecomunnity.exception.ResponseCode.INVALID_TOKEN;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService likeService;
    private final JwtUtil jwtUtil;

    @PatchMapping("/api/posts/{id}/likes")
    public ResponseEntity<ServerResponse> likesPosts(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updatePostLikes(id, userDetails.getUsername());
    }

    @PatchMapping("/movie/comments/{id}/likes")
    public ResponseEntity<ServerResponse> likesComments(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.updateCommentLikes(id, userDetails.getUsername());
    }
}