package com.sparta.moviecomunnity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private User author;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "HEART_ID")
    private List<Heart> hearts;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "COMMENT_ID")
    private List<Comment> comments;

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.hearts = new ArrayList<>();
    }

    public void rewrite(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
