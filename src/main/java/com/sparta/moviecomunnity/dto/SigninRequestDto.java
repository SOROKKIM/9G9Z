package com.sparta.moviecomunnity.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
public class SigninRequestDto {

    // 유저이름
    @Size(min=4,max=10)
    @Pattern(regexp ="^[a-z0-9]*$")
    private String userName;

    // 비밀번호
    @Size(min=8,max=15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9ㄱ-힣]).+$")
    private String password;

}
