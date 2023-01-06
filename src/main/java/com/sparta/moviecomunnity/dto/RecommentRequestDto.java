package com.sparta.moviecomunnity.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RecommentRequestDto {
    private final Long commentId;
    private final String context;
    public RecommentRequestDto(Long commentId, String context) {
        this.commentId = commentId;
        this.context = Objects.requireNonNullElse(context, "");
    }
}
