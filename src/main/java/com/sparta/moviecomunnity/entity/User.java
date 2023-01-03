package com.sparta.moviecomunnity.entity;

import com.sparta.moviecomunnity.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRoleEnum role;


    public User(SignupRequestDto signupRequestDto , UserRoleEnum role) {
        this.username = signupRequestDto.getUserName();
        this.password = signupRequestDto.getPassword();
        this.role = role;
    }
}
