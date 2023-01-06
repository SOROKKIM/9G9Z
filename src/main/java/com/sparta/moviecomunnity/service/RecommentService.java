package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.RecommentRequestDto;
import com.sparta.moviecomunnity.dto.RecommentResponseDto;
import com.sparta.moviecomunnity.entity.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
    private final RecommentRepository recommentRepository;

    // 내가 쓴 대댓글 모아보기
    @Transactional(readOnly = true)
    public List<RecommentResponseDto> getRecomments(User user) {
        List<RecommentResponseDto> toRecomments = new ArrayList<>();

        if(user.getRole() == UserRoleEnum.ADMIN) {
            List<Recomment> allRecomments = recommentRepository.findAllByAvailableTrue(Sort.by(Sort.Direction.ASC, "CreatedAt"));
            if(allRecomments != null) {
                allRecomments.forEach(
                        (recomment) -> toRecomments.add(
                                new RecommentResponseDto(recomment.getId(), recomment.getContext(),
                                        recomment.getUser().getId(), recomment.getComment().getId())
                        )
                );
            }
        }

//        유저일때
        else {
            List<Recomment> recomments = recommentRepository.findAllByUserAndAvailableTrue(user);
            if(recomments != null)  {
                recomments.forEach(
                        (recomment) -> toRecomments.add(
                                new RecommentResponseDto(recomment.getId(), recomment.getContext(),
                                        recomment.getUser().getId(), recomment.getComment().getId())
                        )
                );
            }
        }

        return toRecomments;
    }

    //대댓글 작성
    @Transactional
    public ResponseEntity<ServerResponse> createRecomment(RecommentRequestDto requestDto, Comment comment, User user) {
        Recomment recomment = new Recomment(requestDto.getContext(), user);
        recomment.setComment(comment);
        recommentRepository.save(recomment);
        return ServerResponse.toResponseEntity(SUCCESS_CREATE);
    }

    //대댓글 수정
    @Transactional
    public ResponseEntity<ServerResponse> editRecomment(Long id, String context, String username) {
        Recomment recomment = findRecomment(id);

        // 대댓글 수정은 작성자 본인만 수행할 수 있다.
        if (username.equals(recomment.getUser().getUsername())) {
            recomment.rewrite(context);
            recommentRepository.save(recomment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        return ServerResponse.toResponseEntity(SUCCESS_EDIT);
    }

    //대댓글 삭제
    @Transactional
    public ResponseEntity<ServerResponse> deleteRecomment(Long id, String username, UserRoleEnum role) {
        Recomment recomment = findRecomment(id);

        // 대댓글 삭제는 작성자 본인과 관리자만 수행할 수 있다
        if (username.equals(recomment.getUser().getUsername()) || role.equals(UserRoleEnum.ADMIN)) {
            recomment.delete();
            recommentRepository.save(recomment);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        log.info("delete : {}", recomment);
        return ServerResponse.toResponseEntity(SUCCESS_DELETE);
    }

    @Transactional(readOnly = true)
    public Recomment findRecomment(Long id) {
        Optional<Recomment> optionalRecomment = recommentRepository.findById(id);
        if (optionalRecomment.isEmpty()) {
            throw new CustomException(RECOMMENT_NOT_FOUND);
        }
        Recomment recomment = optionalRecomment.get();

        if (!recomment.isAvailable()) {
            throw new CustomException(RECOMMENT_IS_DELETED);
        }

        return recomment;
    }

    @Transactional(readOnly = true)
    public List<RecommentResponseDto> findRecommentsByCommentId(Long commentId) {
        List<RecommentResponseDto> recommentResponseDtoList = new ArrayList<>();
        List<Recomment> recomments = recommentRepository.findAllByCommentIdAndAvailableTrue(commentId);
        for (Recomment recomment : recomments) {
            RecommentResponseDto recommentResponseDto = new RecommentResponseDto(recomment.getId(), recomment.getContext(),
                    recomment.getUser().getId(), recomment.getComment().getId());
            recommentResponseDtoList.add(recommentResponseDto);
        }
        // 삭제 처리가 되지 않은 댓글만 찾아 반환한다.
        return recommentResponseDtoList;
    }
}
