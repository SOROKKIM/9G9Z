package com.sparta.moviecomunnity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto extends CommentRequestDto{
    private Long postId;
}
