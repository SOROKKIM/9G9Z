package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Recomment;
import com.sparta.moviecomunnity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    public List<Recomment> findAllByUser(User user);

    public Optional<Recomment> findRecommentById(Long recommentId);
}
