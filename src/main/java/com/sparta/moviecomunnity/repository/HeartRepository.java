package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findHeartByUserAndBoardId(User user, Long boardId);
    Optional<Heart> findHeartByUserAndCommentId(User user, Long commentId);

}
