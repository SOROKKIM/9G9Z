package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.SigninRequestDto;
import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    //5가지 첫번째 이 메서드의 기능은?                컨트롤러의 로그인 기능
    //  두번째 이 메서드의 받아야 하는 데이터는 뭔지?    signin ,username , password
    // 세번째  보내줘야하는 데이터는 뭔지? 어떤 결과값을 내보내줄건지?  로그인성공 (String) , / 아이디 또는 비밀번호를 확인해주세요, 로그인 실패
    // 4번째 어떻게 동작을 할지 ? 로직?생각         if / else  : 로그인 성공 혹은 실패를 내보내주고 싶다.
    //5 번째 로직을 하기 위한 필드값 생각
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

