package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class HttpResponseDto {
    private String msg;

    public HttpResponseDto(String msg) {
        this.msg = msg;
    }
}