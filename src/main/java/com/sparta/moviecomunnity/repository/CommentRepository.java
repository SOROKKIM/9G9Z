package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdAndAvailableTrue(long id);
}
