package com.sparta.moviecomunnity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Recomment> recomments;

    @Column
    private boolean available;
    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
        this.hearts = new ArrayList<>();
        this.recomments = new ArrayList<>();
        this.available = true;
    }

    // 편의 메서드
    public void setPost(Post post) {
        if (this.post != post) {
            this.post = post;
            post.addComment(this);
        }
    }

    public void addHeart(Heart heart) {
        if (!this.hearts.contains(heart)) {
            this.hearts.add(heart);
        }

        if (heart.getComment() != this) {
            heart.setComment(this);
        }
    }

    public void addRecomment(Recomment recomment) {
        if (!this.recomments.contains(recomment)) {
            this.recomments.add(recomment);
        }

        if (recomment.getComment() != this) {
            recomment.setComment(this);
        }
    }

    public void edit(String content) {
        this.content = content;
    }

    public void delete() {
        this.available = false;
    }
}
