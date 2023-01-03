package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.dto.SignupResponseDto;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private static final String ADMIN_PASSWORD = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다. 관리자 가입이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        SignupResponseDto SignupResponseDto = userService.signup(signupRequestDto,role);
        return SignupResponseDto;

    }

}
