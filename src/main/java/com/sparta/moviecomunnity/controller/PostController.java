package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.dto.HttpResponseDto;
import com.sparta.moviecomunnity.dto.HttpResponseDtoWithStatusCode;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sparta.moviecomunnity.exception.ErrorCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    @ResponseBody
    public List<PostResponseDto> getAllPostByCreatedTimeAsc() {
        return postService.getAllPostByCreatedAtAsc();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PostResponseDto getPostByPostId(@PathVariable Long id) {
        return postService.getPostByPostId(id);
    }

    @PostMapping("/")
    @ResponseBody
    public HttpResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {

        // 올바른 회원인지 검증
        User author = null;


        // 게시글 작성
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        if (title.trim().equals("")) {
            return new HttpResponseDtoWithStatusCode(400, "게시글 작성에 실패했습니다. 제목이 비어있습니다.");
        } else if (content.trim().equals("")) {
            return new HttpResponseDtoWithStatusCode(400, "게시글 작성에 실패했습니다. 내용이 비어있습니다.");
        }

        postService.createPost(title, content, author);
        return new HttpResponseDto("게시글을 성공적으로 작성하였습니다.");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public HttpResponseDto rewritePost(@PathVariable long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {

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

        return new HttpResponseDto("게시글을 성공적으로 수정하였습니다.");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public HttpResponseDto deletePost(@PathVariable long id) {
        // 올바른 회원인지 검증


        // 게시글 삭제
        postService.deletePost(id);

        return new HttpResponseDto("게시글을 성공적으로 삭제하였습니다.");
    }
}
