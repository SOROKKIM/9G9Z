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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @Column
    private boolean available;

    public Heart(User user, Post post) {
        this.user = user;
        this.post = post;
        this.available = true;
    }

    public Heart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.available = true;
    }

    public void dislike() {
        this.available = false;
    }

    public void like() {
        this.available = true;
    }
}

