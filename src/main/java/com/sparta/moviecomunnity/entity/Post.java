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

    @OneToMany(mappedBy = "post", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    @OneToMany(mappedBy = "post", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    private boolean available;

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.hearts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.available = true;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }

    public void delete() {
        this.available = false;
    }
}
