package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    @ResponseBody
    public List<PostResponseDto> getAllPostOrderByCreatedTimeAsc() {
        return postService.getAllPostOrderByCreatedAtAsc();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PostResponseDto getPostByPostId(@PathVariable Long id) {
        return postService.getPostByPostId(id);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<ServerResponse> createPost(@RequestBody PostRequestDto PostRequestDto, HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 게시글 검증
        String title = PostRequestDto.getTitle();
        String content = PostRequestDto.getContent();
        if (title.trim().equals("")) {
            throw new CustomException(INVALID_POST_TITLE);
        } else if (content.trim().equals("")) {
            throw new CustomException(INVALID_POST_CONTENT);
        }

        postService.createPost(title, content, userDetails);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServerResponse> editPost(@PathVariable long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 게시글 수정
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        if (title.trim().equals("")) {
            throw new CustomException(INVALID_POST_TITLE);
        } else if (content.trim().equals("")) {
            throw new CustomException(INVALID_POST_CONTENT);
        }

        postService.editPost(id, title, content, userDetails);

        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServerResponse> deletePost(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 게시글 삭제
        postService.deletePost(id, userDetails);

        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }
}
