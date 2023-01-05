package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Comment;
import com.sparta.moviecomunnity.entity.Heart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String user;
    private String content;
    @Setter
    private int hearts;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.user = comment.getUser().getUsername();
        this.content = comment.getContent();
    }
}
