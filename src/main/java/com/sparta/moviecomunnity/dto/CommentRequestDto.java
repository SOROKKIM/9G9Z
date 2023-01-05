package com.sparta.moviecomunnity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        if (content == null) {
            this.content = "";
        } else {
            this.content = content;
        }
    }
}
