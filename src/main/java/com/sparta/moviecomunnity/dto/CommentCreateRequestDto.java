package com.sparta.moviecomunnity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommentCreateRequestDto extends CommentRequestDto{
    private Long postId;

    public CommentCreateRequestDto(Long postId, String content) {

    }
}
