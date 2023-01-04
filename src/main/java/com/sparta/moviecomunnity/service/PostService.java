package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.repository.*;
import com.sparta.moviecomunnity.exception.CustomException;
import com.sparta.moviecomunnity.repository.CommentRepository;

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
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostOrderByCreatedAtAsc() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.ASC, "CreatedAt"));
        List<PostResponseDto> responseDtos = new ArrayList<>();

        for (Post post : posts) {
            User author = post.getAuthor();
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment, 0);
                commentResponseDtos.add(commentResponseDto);
            }
            PostResponseDto responseDto = new PostResponseDto(post, commentResponseDtos);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostByPostId(long id) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            User author = post.getAuthor();
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment, 0);
                commentResponseDtos.add(commentResponseDto);
            }
            return new PostResponseDto(post, commentResponseDtos);
        } else {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }
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
    public void editPost(long id, String title, String content, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (!foundPost.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        Post post = foundPost.get();
        if (!post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
        post.rewrite(title, content);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(long id, UserDetailsImpl userDetails) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (!foundPost.isPresent()) {
            throw new CustomException(RESOURCE_NOT_FOUND);
        }

        Post post = foundPost.get();
        if (!post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
        postRepository.delete(post);
    }
}
