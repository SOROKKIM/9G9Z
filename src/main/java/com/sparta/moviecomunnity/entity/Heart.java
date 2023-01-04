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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private Long commentId;

    public Heart(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Heart(User user, Post post, Long commentId) {
        this.user = user;
        this.post = post;
        this.commentId = commentId;
    }
}

