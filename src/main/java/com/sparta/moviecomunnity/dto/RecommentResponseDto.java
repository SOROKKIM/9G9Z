package com.sparta.moviecomunnity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RecommentResponseDto {
    private final long recommentId;
    private final String context;
    private final long userId;
    private final long commentId;
    @Setter
    private int hearts;

    public RecommentResponseDto(long recommentId, String context, long userId, long commentId) {
        this.recommentId = recommentId;
        this.context = context;
        this.userId = userId;
        this.commentId = commentId;
    }
}
