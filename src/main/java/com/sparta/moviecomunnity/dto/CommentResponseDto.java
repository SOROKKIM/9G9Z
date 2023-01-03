package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter

public class CommentResponseDto {
    private Long id;
    private String author;
    private String comment;
    private int hearts;

    public CommentResponseDto(Comment comment, String author, int hearts) {
        this.id = comment.getId();
        this.author = author;
        this.hearts = hearts;

    }

}
