package com.sparta.moviecomunnity.service;

import com.sparta.moviecomunnity.dto.CommentResponseDto;
import com.sparta.moviecomunnity.dto.PostResponseDto;
import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Post;
import com.sparta.moviecomunnity.entity.PostLike;
import com.sparta.moviecomunnity.entity.User;
import com.sparta.moviecomunnity.repository.CommentRepository;
import com.sparta.moviecomunnity.repository.PostLikeRepository;
import com.sparta.moviecomunnity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostByCreatedAtAsc() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.ASC, "CreatedAt"));
        List<PostResponseDto> responseDtos = new ArrayList<>();

        for (Post post : posts) {
            User author = post.getAuthor();
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            PostResponseDto responseDto = new PostResponseDto(post, author.getUsername(), commentResponseDtos);
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
            List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            for (Comment comment : comments) {
                CommentResponseDto singleComment = new CommentResponseDto(comment.getId(), comment.getContent());
                commentResponseDtos.add(singleComment);
            }

            return new PostResponseDto(post, author.getUsername(), commentResponseDtos);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void createPost(String title, String content, User author) {
        Post post = new Post(title, content, author);
        postRepository.saveAndFlush(post);
    }

    @Transactional
    public void rewritePost(long id, String title, String content) {
        Optional<Post> foundPost = postRepository.findPostById(id);
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            post.rewrite(title, content);
            postRepository.saveAndFlush(post);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
    }
}
