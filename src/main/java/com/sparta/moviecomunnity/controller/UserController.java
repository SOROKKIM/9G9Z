package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.SigninRequestDto;
import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sparta.moviecomunnity.jwt.JwtUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie/user")
public class UserController {
    private final UserService userService;
    private static final String ADMIN_PASSWORD = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public ResponseEntity<ServerResponse> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                throw new CustomException(INVALID_INFO);
            }
            role = UserRoleEnum.ADMIN;
        }

        userService.signup(signupRequestDto,role);
        return ServerResponse.toResponseEntity(SUCCESS_SIGNUP);
    }

    //5가지 첫번째 이 메서드의 기능은?                컨트롤러의 로그인 기능
    //  두번째 이 메서드의 받아야 하는 데이터는 뭔지?    signin ,username , password
    // 세번째  보내줘야하는 데이터는 뭔지? 어떤 결과값을 내보내줄건지?  로그인성공 (String) , / 아이디 또는 비밀번호를 확인해주세요, 로그인 실패
    // 4번째 어떻게 동작을 할지 ? 로직?생각         if / else  : 로그인 성공 혹은 실패를 내보내주고 싶다.
    //5 번째 로직을 하기 위한 필드값 생각
    @PostMapping("/signin")
    public ResponseEntity<ServerResponse> signin(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {

       // 사용자 id 및 비밀번호 확인
        String create = userService.signin(signinRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, create);

        return ServerResponse.toResponseEntity(SUCCESS_SIGNIN);
    }
  }

