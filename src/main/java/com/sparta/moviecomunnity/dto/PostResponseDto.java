package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String author;
    private final String title;
    private final String content;
    @Setter
    private long hearts;
    private final List<CommentResponseDto> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post, List<CommentResponseDto> comments) {
        this.id = post.getId();
        this.author = post.getAuthor().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.comments = comments;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}