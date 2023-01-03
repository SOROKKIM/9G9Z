package com.sparta.moviecomunnity.service;



import com.sparta.moviecomunnity.dto.HttpResponseDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.HeartRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
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
    private final PostRepository boardRepository;
    private final CommentRepository commentRepository;

    public HttpResponseDto addBoardLikes(Long boardId, String subject) {
        User user = userRepository.findByUsername(subject).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndBoardId(user, boardId);
        Board board = boardRepository.findById(boardId).get();

        if(heart.isPresent()) {
            board.minusLikes();
            boardRepository.save(board);
            heartRepository.deleteById(heart.get().getId());
        }
        else {
            board.addLikes();
            heartRepository.save(new Heart(user, boardId));
        }

        return new HttpResponseDto("success", 200L);
    }


    public HttpResponseDto addCommentLikes(Long commentId, String subject) {
        User user = userRepository.findByUsername(subject).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        Optional<Heart> heart = heartRepository.findHeartByUserAndCommentId(user, commentId);
        Comment comment = commentRepository.findById(commentId).get();

        if(heart.isPresent()) {
            comment.minusLikes();
            commentRepository.save(comment);
            heartRepository.deleteById(heart.get().getId());
        }
        else {
            comment.addLikes();
            heartRepository.save(new Heart(user, comment.getBoard().getId(), comment.getId()));
        }

        return new HttpResponseDto("success", 200L);
    }
}
