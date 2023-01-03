package com.sparta.moviecomunnity.dto;

public class SignupResponseDto {
    private String msg;
    private int statusCode;

    public SignupResponseDto(String msg , int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
