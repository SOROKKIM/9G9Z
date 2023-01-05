package com.sparta.moviecomunnity.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class CommentRequestDto {
    private final String content;

    public CommentRequestDto(String content) {
        this.content = Objects.requireNonNullElse(content, "");
    }
}
