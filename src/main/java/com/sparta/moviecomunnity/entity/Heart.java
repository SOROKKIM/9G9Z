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
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Comment comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recomment recomment;

    @Column
    private boolean isLike;

    public Heart(User user) {
        this.user = user;
        this.isLike = true;
    }

    //편의 메서드
    public void setRecomment(Recomment recomment) {
        if (this.recomment != recomment) {
            this.recomment = recomment;
            recomment.addHeart(this);
        }
    }

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
        this.isLike = false;
    }

    public void like() {
        this.isLike = true;
    }
}

