package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Comment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String user;
    private final String content;
    @Setter
    private int hearts;

    @Setter
    private List<RecommentResponseDto> recomments;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.user = comment.getUser().getUsername();
        this.content = comment.getContent();
    }
}
