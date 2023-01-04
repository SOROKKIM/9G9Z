package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(long id);
    Optional<Post> findPostByIdAndAvailableTrue(long id);
    List<Post> findAllByAvailableTrue(Sort sort);
}
