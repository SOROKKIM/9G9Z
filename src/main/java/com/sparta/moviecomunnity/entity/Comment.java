package com.sparta.moviecomunnity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private User user;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    public Comment(Post post, String commentContent, User user) {
        this.post = post;
        this.commentContent = commentContent;
        this.user = user;
    }

    public void edit(String commentContent) {
        this.commentContent = commentContent;
    }

}
