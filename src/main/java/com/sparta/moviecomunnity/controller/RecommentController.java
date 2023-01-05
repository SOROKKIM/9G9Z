package com.sparta.moviecomunnity.controller;

import com.sparta.moviecomunnity.dto.RecommentRequestDto;
import com.sparta.moviecomunnity.dto.RecommentResponseDto;
import com.sparta.moviecomunnity.entity.Recomment;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.entity.UserRoleEnum;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.exception.ServerResponse;
import com.sparta.moviecomunnity.repository.RecommentRepository;
import com.sparta.moviecomunnity.repository.UserRepository;
import com.sparta.moviecomunnity.security.UserDetailsImpl;
import com.sparta.moviecomunnity.service.RecommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/recomments")
public class RecommentController {
    private final RecommentService recommentService;
    private final UserRepository userRepository;
    private final RecommentRepository recommentRepository;

    @GetMapping("")
    public List<RecommentResponseDto> getRecomments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return recommentService.getRecomments(userDetails.getUser());
    }

    @PostMapping("")
    public ResponseEntity<ServerResponse> createRecomment(@RequestBody RecommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );
        return recommentService.createRecomment(requestDto, user.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse> editRecomment(@PathVariable long id, @RequestBody RecommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return recommentService.editRecomment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse> deleteRecomment(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            return recommentService.deleteRecomment(id, userDetails.getUser());
    }
}
