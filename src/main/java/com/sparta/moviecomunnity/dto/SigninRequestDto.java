package com.sparta.moviecomunnity.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
public class SigninRequestDto {

    private String username;
    private String password;
}
