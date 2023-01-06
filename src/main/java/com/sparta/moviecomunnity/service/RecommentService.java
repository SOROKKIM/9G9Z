package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.RecommentRequestDto;
import com.sparta.moviecomunnity.dto.RecommentResponseDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.jwt.JwtUtil;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.RecommentRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecommentService {
    private final UserService userService;
    private final CommentService commentService;
    private final RecommentRepository recommentRepository;
    private final JwtUtil jwtUtil;

    // 내가 쓴 대댓글 모아보기
    @Transactional(readOnly = true)
    public List<RecommentResponseDto> getRecomments(User user) {
        List<RecommentResponseDto> toRecomments = new ArrayList<>();

        if(user.getRole() == UserRoleEnum.ADMIN) {
            List<Recomment> allRecomments = recommentRepository.findAll();
            if(allRecomments != null) {
                allRecomments.stream().forEach(
                        (recomment) -> toRecomments.add(
                                new RecommentResponseDto(recomment.getId(), recomment.getContext(),
                                        recomment.getUser().getId(), recomment.getComment().getId(),
                                        recomment.getHearts().size())
                        )
                );
            }
        }

//        유저일때
        else {
            List<Recomment> recomments = recommentRepository.findAllByUser(user);
            if(recomments != null)  {
                recomments.stream().forEach(
                        (recomment) -> toRecomments.add(
                                new RecommentResponseDto(recomment.getId(), recomment.getContext(),
                                        recomment.getUser().getId(), recomment.getComment().getId(),
                                        recomment.getHearts().size())
                        )
                );
            }
        }

        return toRecomments;
    }

    //대댓글 작성
    @Transactional
    public ResponseEntity<ServerResponse> createRecomment(RecommentRequestDto requestDto, String username) {
        User user = userService.findUser(username);

        Comment comment = commentService.findComment(requestDto.getCommentId());

        Recomment recomment = new Recomment(requestDto.getContext(), user);
        recomment.setComment(comment);
        recommentRepository.save(recomment);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    //대댓글 수정
    @Transactional
    public ResponseEntity<ServerResponse> editRecomment(Long id, RecommentRequestDto requestDto, User user) {
        Recomment recomment = recommentRepository.findRecommentById(id).orElseThrow(
                () -> new CustomException(RECOMMENT_NOT_FOUND)
        );

        List<Recomment> recomments = recommentRepository.findAllByUser(user);
        if(recomments == null) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        String newContext = requestDto.getContext();

        if ( newContext != null || !newContext.isEmpty() ) {
            recomment.rewrite(requestDto.getContext());
            recommentRepository.save(recomment);
        }

        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    //대댓글 삭제
    @Transactional
    public ResponseEntity<ServerResponse> deleteRecomment(long id, User user) {
        Recomment recomment = recommentRepository.findById(id).orElseThrow(
                () -> new CustomException(RECOMMENT_NOT_FOUND)
        );

        if(user.getRole() != UserRoleEnum.ADMIN && !recomment.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException((INVALID_AUTH_TOKEN));
        }

        log.info("delete : {}", recomment.toString());
        recommentRepository.deleteById(id);
        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }


    public Recomment getRecomment(Long id) {
        Recomment recomment = recommentRepository.findById(id).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );
        return recomment;
    }

}
