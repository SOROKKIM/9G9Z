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

    public Heart(User user) {
        this.user = user;
        this.available = true;
    }

    //편의 메서드
    public void setComment(Comment comment) {
        if (this.comment != comment) {
            this.comment = comment;
            comment.addHeart(this);
        }
    }

    public void setPost(Post post) {
        if (this.post != post) {
            this.post = post;
            post.addHeart(this);
        }
    }

    public void dislike() {
        this.available = false;
    }

    public void like() {
        this.available = true;
    }
}

