package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(long id);
    List<Comment> findAllByPostId(long id);
}
