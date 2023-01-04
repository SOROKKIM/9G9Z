package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.RecommentRequestDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.RecommentRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RequiredArgsConstructor
@Service
public class RecommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final JwtUtil jwtUtil;

    // 대댓글 모아보기
    public List<Recomment> getRecomments(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<Recomment> recomments = recommentRepository.findAllByUser(user.get());
        return recomments;
    }

    //대댓글 작성
    @Transactional
    public ResponseEntity<ServerResponse> createRecomment(RecommentRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Comment comment = commentRepository.findCommentById(requestDto.getCommentId()).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );

        Recomment recomment = new Recomment(requestDto.getContext(), user, comment);
        recommentRepository.save(recomment);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    //대댓글 수정
    @Transactional
    public ResponseEntity<ServerResponse> editRecomment(Long id, RecommentRequestDto requestDto) {
        Recomment recomment = recommentRepository.findRecommentById(id).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );
        recomment.rewrite(requestDto.getContext());
        recommentRepository.save(recomment);
        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    //대댓글 삭제
    @Transactional
    public ResponseEntity<ServerResponse> deleteRecomment(long id) {
        Recomment recomment = recommentRepository.findById(id).orElseThrow(
                () -> new CustomException(RESOURCE_NOT_FOUND)
        );
        recommentRepository.deleteById(id);
        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }


}
