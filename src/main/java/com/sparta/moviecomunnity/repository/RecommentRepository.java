package com.sparta.moviecomunnity.repository;

import com.sparta.moviecomunnity.entity.Recomment;
import com.sparta.moviecomunnity.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    List<Recomment> findAllByAvailableTrue(Sort sort);

    List<Recomment> findAllByUserAndAvailableTrue(User user);

    List<Recomment> findAllByCommentIdAndAvailableTrue(Long commentId);

}
