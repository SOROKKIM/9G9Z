package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.HttpResponseDto;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.service.HeartService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/api/boards/{id}/likes")
    public HttpResponseDto likesPosts(@PathVariable String id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String subject = claims.getSubject();
                return likeService.updatePostLikes(Long.valueOf(id), subject);
            }
        }
        throw new CustomException(INVALID_TOKEN);
    }

    @PatchMapping("/api/comments/{id}/likes")
    public HttpResponseDto likesComments(@PathVariable String id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String subject = claims.getSubject();
                return likeService.updateCommentLikes(Long.valueOf(id), subject);
            }
        }
        throw new CustomException(INVALID_TOKEN);
    }

}