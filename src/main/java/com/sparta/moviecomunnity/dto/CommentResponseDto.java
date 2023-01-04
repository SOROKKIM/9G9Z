package com.sparta.moviecomunnity.dto;

import com.sparta.moviecomunnity.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String user;
    private String commentContents;
    private int hearts;

    public CommentResponseDto(Comment comment, int hearts) {
        this.id = comment.getId();
        this.user = comment.getUser().getUsername();
        this.commentContents = comment.getCommentContent();
        this.hearts = hearts;
    }
}
