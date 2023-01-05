package com.sparta.moviecomunnity.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PostRequestDto {
    private final String title;
    private final String content;

    public PostRequestDto(String title, String content) {
        this.title = Objects.requireNonNullElse(title, "");
        this.content = Objects.requireNonNullElse(content, "");
    }
}