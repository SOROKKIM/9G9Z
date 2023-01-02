package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
}
