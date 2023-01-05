package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.SigninRequestDto;
import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.sparta.moviecomunnity.jwt.JwtUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Objects;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/users")
public class UserController {
    private final UserService userService;
    private static final String ADMIN_PASSWORD = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public ResponseEntity<ServerResponse> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        // 전달 받은 아이디와 패스워드가 요구되는 패턴과 일치하지 않는 경우 예외처리
        if (bindingResult.hasErrors() && bindingResult.getAllErrors().stream().findFirst().isPresent()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            String errorCode = Objects.requireNonNull(objectError.getCodes())[1].split("\\.")[1];
            System.out.println("Signup Pattern Error:" + errorCode);
            if (errorCode.equals("username")) {
                throw new CustomException(INVALID_ID_PATTERN);
            } else if (errorCode.equals("password")) {
                throw new CustomException(INVALID_PASSWORD_PATTERN);
            }
        }

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                throw new CustomException(INVALID_ADMIN_PASSWORD);
            }
            role = UserRoleEnum.ADMIN;
        }

        userService.signup(signupRequestDto, role);
        return ServerResponse.toResponseEntity(SUCCESS_SIGNUP);
    }

    @PostMapping("/signin")
    public ResponseEntity<ServerResponse> signin(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {

        if (signinRequestDto.getUsername() == null || signinRequestDto.getUsername().trim().equals("")) {
            throw new CustomException(INVALID_SIGNIN_ID);
        } else if (signinRequestDto.getPassword() == null || signinRequestDto.getPassword().trim().equals("")) {
            throw new CustomException(INVALID_SIGNIN_PASSWORD);
        }

        // 사용자 id 및 비밀번호 확인
        String createToken = userService.signin(signinRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        return ServerResponse.toResponseEntity(SUCCESS_SIGNIN);
    }


}

