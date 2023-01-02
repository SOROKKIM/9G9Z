package com.sparta.moviecomunnity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Heart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private Long boardId;

    private Long commentId;

    public Heart(User user, Long boardId) {
        this.user = user;
        this.boardId = boardId;
    }

    public Heart(User user, Long boardId, Long commentId) {
        this.user = user;
        this.boardId = boardId;
        this.commentId = commentId;
    }
}

