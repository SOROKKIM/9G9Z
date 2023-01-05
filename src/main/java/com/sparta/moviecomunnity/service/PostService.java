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
    private final UserRepository userRepository;
    private final HeartService heartService;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostOrderByCreatedAtAsc() {
        List<Post> posts = postRepository.findAllByAvailableTrue(Sort.by(Sort.Direction.ASC, "CreatedAt"));
        List<PostResponseDto> responseDtos = new ArrayList<>();

        for (Post post : posts) {
            responseDtos.add(getPostResponseDto(post));
        }

        return responseDtos;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostByPostId(long id) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            if (!post.isAvailable()) {
                throw new CustomException(POST_IS_DELETED);
            }

            return getPostResponseDto(post);
        } else {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }
    }

    private PostResponseDto getPostResponseDto(Post post) {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            if (comment.isAvailable()) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
                commentResponseDtos.add(commentResponseDto);
            }
        }
        PostResponseDto responseDto = new PostResponseDto(post, commentResponseDtos);
        responseDto.setHearts(heartService.getPostHeartCount(post));
        return responseDto;
    }

    @Transactional
    public void createPost(String title, String content, UserDetailsImpl userDetails) {

        Optional<User> foundAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (!foundAuthor.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        User author = foundAuthor.get();
        Post post = new Post(title, content, author);
        postRepository.save(post);
    }

    @Transactional
    public void editPost(long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (!foundPost.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        Optional<User> foundAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (!foundAuthor.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        Post post = foundPost.get();
        User author = foundAuthor.get();

        if (post.getAuthor().getUsername().equals(author.getUsername())) {
            String title = requestDto.getTitle();
            String content = requestDto.getContent();
            if (title.trim().isEmpty() && content.trim().isEmpty()) {
                throw new CustomException(INVALID_EDIT_VALUE);
            }

            if (title.trim().isEmpty()) {
                title = post.getTitle();
            }

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
    public void deletePost(long id, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (!foundPost.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        Optional<User> foundAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (!foundAuthor.isPresent()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        Post post = foundPost.get();
        User author = foundAuthor.get();

        if (!post.isAvailable()) {
            throw new CustomException(POST_IS_DELETED);
        }

        if (post.getAuthor().getUsername().equals(author.getUsername()) || author.getRole().equals(UserRoleEnum.ADMIN)) {
            post.delete();
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                comment.delete();
                List<Heart> hearts = comment.getHearts();
                for (Heart heart : hearts) {
                    heart.dislike();
                    //heartRepository.save(heart);
                }
                //commentRepository.save(comment);
            }
            List<Heart> hearts = post.getHearts();
            for (Heart heart : hearts) {
                heart.dislike();
                //heartRepository.save(heart);
            }
            postRepository.save(post);
        } else {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
    }
}
