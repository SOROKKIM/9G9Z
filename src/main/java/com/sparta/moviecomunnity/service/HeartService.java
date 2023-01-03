package com.sparta.moviecomunnity.service;



import com.sparta.moviecomunnity.dto.HttpResponseDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.HeartRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ErrorCode.MEMBER_NOT_FOUND;

@Transactional
@Service
@RequiredArgsConstructor
public class HeartService {
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;

    public HttpResponseDto updatePostLikes(Long boardId, String subject) {
        User user = userRepository.findByUsername(subject).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndBoardId(user, boardId);

        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
        }
        else {
            heartRepository.save(new Heart(user, boardId));
        }

        return new HttpResponseDto("success", 200L);
    }


    public HttpResponseDto updateCommentLikes(Long commentId, String subject) {
        User user = userRepository.findByUsername(subject).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndCommentId(user, commentId);
        Comment comment = commentRepository.findById(commentId).get(); // 클라이언트는 클릭으로 좋아요 하기 때문에 없는 댓글에 좋아요를 할 수 없다. null일때의 예외처리 x

        if(heart.isPresent()) {
            heartRepository.deleteById(heart.get().getId());
        }
        else {
            heartRepository.save(new Heart(user, comment.getPost().getId(), commentId));
        }

        return new HttpResponseDto("success", 200L);
    }
}
