package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;

    public PostRequestDto(String title, String content) {
        if (title == null) {
            this.title = "";
        } else {
            this.title = title;
        }

        if (content == null) {
            this.content = "";
        } else {
            this.content = content;
        }
    }
}