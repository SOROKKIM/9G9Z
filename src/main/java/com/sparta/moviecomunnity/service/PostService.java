package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.dto.PostRequestDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.*;

import com.sparta.moviecomunnity.repository.*;
import com.sparta.moviecomunnity.exception.CustomException;

import com.sparta.moviecomunnity.security.UserDetailsImpl;
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
    private final CommentService commentService;
    private final UserService userService;
    private final HeartService heartService;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostOrderByCreatedAtAsc() {
        List<Post> posts = postRepository.findAllByAvailableTrue(Sort.by(Sort.Direction.ASC, "CreatedAt"));
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            responseDtoList.add(getPostResponseDto(post));
        }

        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostByPostId(Long id) {
        Optional<Post> optionalPost = postRepository.findPostById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (!post.isAvailable()) {
                throw new CustomException(POST_IS_DELETED);
            }
            return getPostResponseDto(post);
        } else {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }
    }

    private PostResponseDto getPostResponseDto(Post post) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> comments = commentService.findComments(post.getId());
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDto.setHearts(heartService.getCommentHeartCount(comment));
            commentResponseDtoList.add(commentResponseDto);
        }
        PostResponseDto responseDto = new PostResponseDto(post, commentResponseDtoList);
        responseDto.setHearts(heartService.getPostHeartCount(post));
        return responseDto;
    }

    @Transactional
    public void createPost(String title, String content, UserDetailsImpl userDetails) {
        User author = userService.findUser(userDetails.getUsername());
        Post post = new Post(title, content, author);
        postRepository.save(post);
    }

    @Transactional
    public void editPost(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        User author = userService.findUser(userDetails.getUsername());
        Post post = getPost(id);

        // 수정은 오직 작성자 본인만 가능하다.
        if (post.getAuthor().getUsername().equals(author.getUsername())) {


            String title = requestDto.getTitle();
            String content = requestDto.getContent();
            // title과 content 모두 빈 칸이면 예외를 발생시킨다.
            if (title.trim().isEmpty() && content.trim().isEmpty()) {
                throw new CustomException(INVALID_EDIT_VALUE);
            }

            // title만 빈 칸이면 원본 내용을 유지한다.
            if (title.trim().isEmpty()) {
                title = post.getTitle();
            }

            // content만 빈 칸이면 원본 내용을 유지한다.
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
    public void deletePost(Long id, UserDetailsImpl userDetails) {
        User author = userService.findUser(userDetails.getUsername());
        Post post = getPost(id);

        // 삭제는 게시글 작성자 혹은 관리자라면 가능하다.
        if (post.getAuthor().getUsername().equals(author.getUsername()) || author.getRole().equals(UserRoleEnum.ADMIN)) {
            post.delete();

            // 연관된 모든 코멘트도 삭제 처리 한다.
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                comment.delete();

                // 연관된 코멘트의 모든 좋아요도 삭제 처리 한다.
                List<Heart> hearts = comment.getHearts();
                for (Heart heart : hearts) {
                    heart.dislike();
                }
            }

            // 연관된 모든 좋아요도 삭제 처리 한다.
            List<Heart> hearts = post.getHearts();
            for (Heart heart : hearts) {
                heart.dislike();
            }

            postRepository.save(post);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }

    private Post getPost(Long id) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (foundPost.isEmpty()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        Post post = foundPost.get();
        if (!post.isAvailable()) {
            throw new CustomException(POST_IS_DELETED);
        }
        return post;
    }

}
