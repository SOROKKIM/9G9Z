package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.*;

import com.sparta.moviecomunnity.repository.*;
import com.sparta.moviecomunnity.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.moviecomunnity.exception.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostOrderByCreatedAtAsc() {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByAvailableTrue(Sort.by(Sort.Direction.ASC, "CreatedAt"));
        for (Post post : posts) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostByPostId(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    @Transactional
    public void createPost(String title, String content, User author) {
        Post post = new Post(title, content, author);
        postRepository.save(post);
    }

    @Transactional
    public void editPost(Long id, PostRequestDto requestDto, String username) {
        Post post = findPost(id);

        // 수정은 오직 작성자 본인만 가능하다.
        if (post.getAuthor().getUsername().equals(username)) {

            String title = requestDto.getTitle();
            String content = requestDto.getContent();
            // 제목과 내용 모두 빈 칸이면 예외를 발생시킨다.
            if (title.trim().isEmpty() && content.trim().isEmpty()) {
                throw new CustomException(INVALID_EDIT_VALUE);
            }

            // 제목만 빈 칸이면 원본 내용을 유지한다.
            if (title.trim().isEmpty()) {
                title = post.getTitle();
            }

            // 제목만 빈 칸이면 원본 내용을 유지한다.
            if (content.trim().isEmpty()) {
                content = post.getContent();
            }

            post.edit(title, content);
            postRepository.save(post);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    @Transactional
    public void deletePost(Long id, String username, UserRoleEnum role) {
        Post post = findPost(id);

        // 삭제는 게시글 작성자 혹은 관리자라면 가능하다.
        if (post.getAuthor().getUsername().equals(username) || role.equals(UserRoleEnum.ADMIN)) {
            post.delete();

            // 연관된 모든 코멘트도 삭제 처리 한다.
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                comment.delete();

                // 연관된 코멘트의 모든 리코멘트도 삭제 처리 한다.
                List<Recomment> recomments = comment.getRecomments();
                for (Recomment recomment : recomments) {
                    recomment.delete();
                }
            }

            postRepository.save(post);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    public Post findPost(Long id) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (foundPost.isEmpty()) {
            throw new CustomException(POST_NOT_FOUND);
        }

        Post post = foundPost.get();
        if (!post.isAvailable()) {
            throw new CustomException(POST_IS_DELETED);
        }

        return post;
    }

}
