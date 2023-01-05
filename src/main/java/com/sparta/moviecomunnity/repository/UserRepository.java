package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.ServerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User> findByUsername(String username);
//    User findByUser(String username);
}

