package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto extends CommentRequestDto {
    private final Long postId;

    public CommentCreateRequestDto(Long postId, String content) {
        super(content);
        this.postId = postId;
    }
}