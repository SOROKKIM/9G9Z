package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/")
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
    public ResponseEntity<ServerResponse> createPost(@RequestBody PostRequestDto PostRequestDto, HttpServletRequest request) {

        // 올바른 회원인지 검증
        User author = new User("testName", "testPassword", UserRoleEnum.USER);


        // 게시글 작성
        String title = PostRequestDto.getTitle();
        String content = PostRequestDto.getContent();
        if (title.trim().equals("")) {
            throw new CustomException(INVALID_POST_TITLE);
        } else if (content.trim().equals("")) {
            throw new CustomException(INVALID_POST_CONTENT);
        }

        postService.createPost(title, content, author);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServerResponse> editPost(@PathVariable long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {

        // 올바른 회원인지 검증


        // 게시글 수정
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        if (title.trim().equals("")) {
            throw new CustomException(INVALID_POST_TITLE);
        } else if (content.trim().equals("")) {
            throw new CustomException(INVALID_POST_CONTENT);
        }

        postService.rewritePost(id, title, content);

        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServerResponse> deletePost(@PathVariable long id) {
        // 올바른 회원인지 검증


        // 게시글 삭제
        postService.deletePost(id);

        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }
}
