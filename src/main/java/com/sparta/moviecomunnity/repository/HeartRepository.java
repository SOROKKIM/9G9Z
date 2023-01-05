package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findHeartByUserAndPost(User user, Post post);
    Optional<Heart> findHeartByUserAndComment(User user, Comment comment);
    Optional<Heart> findHeartByUserAndRecomment(User user, Recomment recomment);

}
