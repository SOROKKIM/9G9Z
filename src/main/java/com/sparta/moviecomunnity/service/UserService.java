package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.SigninRequestDto;
import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.dto.SignupResponseDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.UserIdNotExistException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.INVALID_INFO;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto, UserRoleEnum role) {

        // 회원 중복 확인
        Optional<User> findUserId = userRepository.findByUsername(signupRequestDto.getUserName());
        if(findUserId.isPresent()){
            throw new CustomException(INVALID_INFO);
        }
        User user = new User(signupRequestDto.getUserName(), signupRequestDto.getPassword(), role);
        userRepository.save(user);
    }


    public String signin(SigninRequestDto signinRequestDto) {
        String username = signinRequestDto.getUserName();
        String password = signinRequestDto.getPassword();
        // 아이디 및 비밀먼호 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () ->new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if (!user.getPassword().equals(password) ) {
        throw new IllegalArgumentException("비밀번호가 불일치 합니다.");
        } else {
            String createdToken = jwtUtil.createToken(user.getUsername(),user.getRole());
            return createdToken;
        }
    }
}
