package com.sparta.moviecomunnity.dto;

public class SigninResponseDto {

    private String msg;
    private int statusCode;

    public SigninResponseDto(String msg , int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
