package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private long hearts;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post, List<CommentResponseDto> comments) {
        this.id = post.getId();
        //this.author = post.getAuthor().getUsername();
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.hearts = post.getHearts().size();
        this.comments = comments;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}