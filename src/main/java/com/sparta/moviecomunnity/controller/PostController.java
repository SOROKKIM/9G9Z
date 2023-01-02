package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.dto.ResponseDto;
import com.sparta.moviecomunnity.dto.ResponseDtoWithStatusCode;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
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
    public ResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {

        // 올바른 회원인지 검증
        User author = null;


        // 게시글 작성
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        if (title.trim().equals("")) {
            return new ResponseDtoWithStatusCode(400, "게시글 작성에 실패했습니다. 제목이 비어있습니다.");
        } else if (content.trim().equals("")) {
            return new ResponseDtoWithStatusCode(400, "게시글 작성에 실패했습니다. 내용이 비어있습니다.");
        }

        postService.createPost(title, content, author);
        return new ResponseDto("게시글을 성공적으로 작성하였습니다.");
    }
}
