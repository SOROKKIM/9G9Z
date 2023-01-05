package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.SignInRequestDto;
import com.sparta.moviecomunnity.dto.SignUpRequestDto;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final String ADMIN_PASSWORD = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/sign-up")
    public ResponseEntity<ServerResponse> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
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
        if (signUpRequestDto.isAdmin()) {
            assert signUpRequestDto.getAdminPassword() != null;
            if (!signUpRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                throw new CustomException(INVALID_ADMIN_PASSWORD);
            }
            role = UserRoleEnum.ADMIN;
        }

        userService.signUp(signUpRequestDto, role);
        return ServerResponse.toResponseEntity(SUCCESS_SIGNUP);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ServerResponse> signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        String username = signInRequestDto.getUsername();
        String password = signInRequestDto.getPassword();

        if (username == null || username.trim().equals("")) {
            throw new CustomException(INVALID_SIGN_IN_ID);
        } else if (password == null || password.trim().equals("")) {
            throw new CustomException(INVALID_SIGN_IN_PASSWORD);
        }

        // 사용자 id 및 비밀번호 확인
        String createToken = userService.signIn(signInRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        return ServerResponse.toResponseEntity(SUCCESS_SIGNIN);
    }
}

