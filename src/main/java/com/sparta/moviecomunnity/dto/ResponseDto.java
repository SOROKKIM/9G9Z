package com.sparta.moviecomunnity.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String msg;

    public ResponseDto(String msg) {
        this.msg = msg;
    }
}