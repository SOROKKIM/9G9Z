package com.sparta.moviecomunnity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@AllArgsConstructor
public class SignupRequestDto {

    // 유저이름
    @Size(min=4,max=10)
    @Pattern(regexp ="^[a-z0-9]*$")
    @NotNull
    private String username;

    // 비밀번호
    @Size(min=8,max=15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9ㄱ-힣]).+$")
    @NotNull
    private String password;

    //관리자 권한
    private boolean admin = false;

    //관리자 패스워드
    private String adminPassword ="";


}
