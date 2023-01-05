package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.SignInRequestDto;
import com.sparta.moviecomunnity.dto.SignUpRequestDto;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignUpRequestDto signupRequestDto, UserRoleEnum role) {

        // 회원 중복 확인
        Optional<User> findUserId = userRepository.findByUsername(signupRequestDto.getUsername());
        if(findUserId.isPresent()){
            throw new CustomException(DUPLICATE_USER);
        }
        User user = new User(signupRequestDto.getUsername(), signupRequestDto.getPassword(), role);
        userRepository.save(user);
    }


    public String signIn(SignInRequestDto signinRequestDto) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        // 아이디 및 비밀먼호 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        //로그인을 할 수 있는 사용자인지 확인을 한다.
        if(!user.isUserStatus()){
            throw new CustomException(MEMBER_IS_UNREGISTER);
        }else {
            if (!user.getPassword().equals(password) ) {
                throw new CustomException(INVALID_PASSWORD);
            } else {
                return jwtUtil.createToken(user.getUsername(),user.getRole());
            }
        }
    }

    public User findUser(String username) {
        Optional<User> optionalAuthor = userRepository.findByUsername(username);
        if (optionalAuthor.isEmpty()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        return optionalAuthor.get();
    }

}
