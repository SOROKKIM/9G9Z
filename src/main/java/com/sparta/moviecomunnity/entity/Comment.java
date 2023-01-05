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

    public Comment(Post post, String content, User user) {
        this.post = post;
        this.content = content;
        this.user = user;
        this.hearts = new ArrayList<>();
        this.available = true;
    }

    // 편의 메서드
    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        if (heart.getComment() != this) {
            heart.setComment(this);
        }
    }

    public void edit(String content) {
        this.content = content;
    }

    public void delete() {
        this.available = false;
    }
}
