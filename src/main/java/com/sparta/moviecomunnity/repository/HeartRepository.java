package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findHeartByUserAndPost(User user, Post post);
    Optional<Heart> findHeartByUserAndComment(User user, Comment comment);
    Integer countByPost(Post post);
}
