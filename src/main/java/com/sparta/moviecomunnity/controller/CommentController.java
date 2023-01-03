package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.CommentRequestDto;
import com.sparta.moviecomunnity.dto.ResponseDto;
import com.sparta.moviecomunnity.dto.ResponseDtoWithStatusCode;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.sparta.moviecomunnity.exception.ErrorCode.INVALID_AUTH_TOKEN;

@RestController
@RequestMapping("/movie/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    @ResponseBody
    @PostMapping("")
    public ResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {

        // 올바른 회원인지 검증
        User author = null;


        // 댓글 작성
        String comment = commentRequestDto.getCommentContent();
        if (comment.trim().equals("")) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        commentService.createComment(commentRequestDto, author);
        return new ResponseDto("댓글을 성공적으로 작성하였습니다.");
    }


    // 댓글 수정
    @ResponseBody
    @PutMapping("/movie/comments/{id}")
    public ResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {

        // 올바른 회원인지 검증


        // 댓글 수정
        String comment = commentRequestDto.getCommentContent();
        if (comment.trim().equals("")) {
            return new ResponseDtoWithStatusCode(400, "댓글 수정에 실패했습니다. 내용이 비어있습니다.");
        }

        try {
            commentService.updateComment(id, commentRequestDto);
        } catch (IllegalArgumentException e) {
            return new ResponseDtoWithStatusCode(400, "댓글 수정에 실패했습니다. 댓글을 찾을 수 없습니다.");
        }

        return new ResponseDto("댓글을 성공적으로 수정하였습니다.");
    }


    // 댓글 삭제
    @ResponseBody
    @DeleteMapping("/movie/comments/{id}")
    public ResponseDto deleteComment(@PathVariable Long id) {

        // 올바른 회원인지 검증


        // 게시글 삭제
        try {
            commentService.deleteComment(id);
        } catch (IllegalArgumentException e) {
            return new ResponseDtoWithStatusCode(400, "댓글 삭제에 실패했습니다. 댓글을 찾을 수 없습니다.");
        }

        return new ResponseDto("댓글을 성공적으로 삭제하였습니다.");
    }


    //댓글 좋아요
//    public CommentResponseDto likeOrDislikeComment(@PathVariable Long id, HttpServletRequest request) {
//        return commentService.likeOrDislikeComment(id,request);
//    }

}
