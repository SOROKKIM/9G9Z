package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequestMapping("/movie/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<ServerResponse> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String comment = commentRequestDto.getCommentContent();
        if (comment.trim().equals("")) {
            throw new CustomException(INVALID_CONTENT);
        }

        commentService.createComment(commentRequestDto, userDetails);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }


    // 댓글 수정
    @ResponseBody
    @PutMapping("/movie/comments/{id}")
    public ResponseEntity<ServerResponse> editComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String comment = commentRequestDto.getCommentContent();
        if (comment.trim().equals("")) {
            throw new CustomException(INVALID_CONTENT);
        }

        try {
            commentService.editComment(id, commentRequestDto, userDetails);
        } catch (IllegalArgumentException e) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }


    // 댓글 삭제
    @ResponseBody
    @DeleteMapping("/movie/comments/{id}")
    public ResponseEntity<ServerResponse> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        // 게시글 삭제
        try {
            commentService.deleteComment(id, userDetails);
        } catch (IllegalArgumentException e) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }


    //댓글 좋아요
//    public CommentResponseDto likeOrDislikeComment(@PathVariable Long id, HttpServletRequest request) {
//        return commentService.likeOrDislikeComment(id,request);
//    }

}
