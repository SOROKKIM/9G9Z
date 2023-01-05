package com.sparta.moviecomunnity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RecommentResponseDto {
    private long recommentId;
    private String context;
    private long userId;
    private long commentId;
    private long hearts;

    public RecommentResponseDto(long recommentId, String context, long userId, long commentId, long hearts) {
        this.recommentId = recommentId;
        this.context = context;
        this.userId = userId;
        this.commentId = commentId;
        this.hearts = hearts;
    }
}
