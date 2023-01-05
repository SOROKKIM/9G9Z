package com.sparta.moviecomunnity.service;


import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class MyInformationService {
    private final UserRepository userRepository;

    @Transactional
    public void unregister(String userName) {

//        User user2 = userRepository.findByUsername(userName).orElseThrow(IllegalAccessError::new);

        Optional<User> user = userRepository.findByUsername(userName);
        if(user.isEmpty()){
            throw new CustomException(MEMBER_NOT_FOUND); //에러코드
        }else {
            user.get().modifyUserStatus(false);
            userRepository.save(user.get());
        }
    }

}
