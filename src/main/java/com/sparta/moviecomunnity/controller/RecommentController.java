package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.RecommentRequestDto;
import com.sparta.moviecomunnity.dto.RecommentResponseDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.HeartRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.CommentService;
import com.sparta.moviecomunnity.service.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recomments")
public class RecommentController {
    private final RecommentService recommentService;
    private final CommentService commentService;
    private final HeartRepository heartRepository;

    @GetMapping("")
    public List<RecommentResponseDto> getRecomments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<RecommentResponseDto> recommentResponseDtoList = recommentService.getRecomments(userDetails.getUser());
        for (RecommentResponseDto recommentResponseDto : recommentResponseDtoList) {
            recommentResponseDto.setHearts(heartRepository.countByRecommentIdAndIsLikeTrue(recommentResponseDto.getRecommentId()));
        }
        return recommentResponseDtoList;
    }

    @PostMapping("")
    public ResponseEntity<ServerResponse> createRecomment(@RequestBody RecommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String context = requestDto.getContext();
        if (context.trim().equals("")) {
            throw new CustomException(INVALID_CONTENT);
        }
        Comment comment = commentService.findComment(requestDto.getCommentId());
        return recommentService.createRecomment(requestDto, comment, userDetails.getUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse> editRecomment(@PathVariable long id, @RequestBody RecommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String context = requestDto.getContext();
        if (context.trim().equals("")) {
            throw new CustomException(INVALID_CONTENT);
        }
        return recommentService.editRecomment(id, context, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse> deleteRecomment(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            return recommentService.deleteRecomment(id, userDetails.getUsername(), userDetails.getUser().getRole());
    }
}
