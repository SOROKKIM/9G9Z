package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.SignupRequestDto;
import com.sparta.moviecomunnity.dto.SignupResponseDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.UserIdNotExistException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto, UserRoleEnum role) {

        // 회원 중복 확인
        Optional<User> findUserId = userRepository.findByUsername(signupRequestDto.getUserName());
        if(findUserId.isPresent()){
            throw new UserIdNotExistException();
        }
        User user = new User(signupRequestDto.getUserName(), signupRequestDto.getPassword(), role);
        userRepository.save(user);
        return new SignupResponseDto("회원가입 완료",200);
    }


}
