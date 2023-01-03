package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class HttpResponseDtoWithStatusCode extends HttpResponseDto {
    private int statusCode;

    public HttpResponseDtoWithStatusCode(int statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }
}