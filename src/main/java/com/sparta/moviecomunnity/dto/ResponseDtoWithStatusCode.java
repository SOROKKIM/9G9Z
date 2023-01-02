package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class ResponseDtoWithStatusCode extends ResponseDto {
    private int statusCode;

    public ResponseDtoWithStatusCode(int statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }
}