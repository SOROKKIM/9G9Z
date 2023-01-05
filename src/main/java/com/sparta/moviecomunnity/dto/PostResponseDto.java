package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Heart;
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
        this.author = post.getAuthor().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.hearts = 0;
        List<Heart> hearts = post.getHearts();
        for (Heart heart : hearts) {
            if (heart.isAvailable()) {
                this.hearts++;
            }
        }
        this.comments = comments;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}