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
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private User user;

    @Column
    private boolean available;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Recomment> recomments;

    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
        this.hearts = new ArrayList<>();
        this.available = true;
    }

    // 편의 메서드
    public void setPost(Post post) {
        if (this.post != post) {
            this.post.getComments().remove(this);
            this.post = post;
            post.addComment(this);
        }
    }
    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        if (heart.getComment() != this) {
            heart.setComment(this);
        }
    }

    public void addRecomment(Recomment recomment) {
        this.recomments.add(recomment);
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
