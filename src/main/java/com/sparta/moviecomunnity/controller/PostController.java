package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.CommentService;
import com.sparta.moviecomunnity.service.HeartService;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final HeartService heartService;

    @GetMapping("")
    public List<PostResponseDto> getAllPostOrderByCreatedTimeAsc() {
        List<PostResponseDto> postResponseDtoList = postService.getAllPostOrderByCreatedAtAsc();
        for (PostResponseDto postResponseDto : postResponseDtoList) {
            makePostResponseDto(postResponseDto);
        }

        return postResponseDtoList;
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostByPostId(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.getPostByPostId(id);
        return makePostResponseDto(postResponseDto);
    }

    private PostResponseDto makePostResponseDto(PostResponseDto postResponseDto) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findCommentsByPostId(postResponseDto.getId());
        for (CommentResponseDto commentResponseDto : commentResponseDtoList) {
            commentResponseDto.setHearts(heartService.getCommentHeartCount(commentResponseDto.getId()));
        }
        postResponseDto.setComments(commentResponseDtoList);
        postResponseDto.setHearts(heartService.getPostHeartCount(postResponseDto.getId()));
        return postResponseDto;
    }

    @PostMapping("")
    public ResponseEntity<ServerResponse> createPost(@RequestBody PostRequestDto PostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 게시글 검증
        String title = PostRequestDto.getTitle();
        String content = PostRequestDto.getContent();
        if (title.trim().equals("")) {
            throw new CustomException(INVALID_POST_TITLE);
        }

        if (content.trim().equals("")) {
            throw new CustomException(INVALID_CONTENT);
        }

        postService.createPost(title, content, userDetails.getUser());
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse> editPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 게시글 수정
        postService.editPost(id, requestDto, userDetails.getUsername());
        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 게시글 삭제
        postService.deletePost(id, userDetails.getUsername(), userDetails.getUser().getRole());
        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }
}