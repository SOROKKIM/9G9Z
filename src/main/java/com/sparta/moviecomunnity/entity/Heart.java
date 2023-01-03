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
    private Long postId;

    private Long commentId;

    public Heart(User user, Long postId) {
        this.user = user;
        this.postId = postId;
    }

    public Heart(User user, Long postId, Long commentId) {
        this.user = user;
        this.postId = postId;
        this.commentId = commentId;
    }
}

