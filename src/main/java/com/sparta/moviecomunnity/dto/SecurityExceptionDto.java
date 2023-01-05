package com.sparta.moviecomunnity.dto;


import lombok.Getter;

@Getter
public class SecurityExceptionDto {
    private final int statusCode;
    private final String msg;

    public SecurityExceptionDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
