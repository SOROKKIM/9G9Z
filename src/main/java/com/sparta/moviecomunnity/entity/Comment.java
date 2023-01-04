package com.sparta.moviecomunnity.entity;

import com.sparta.moviecomunnity.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


    public Comment(Post post, String commentContent, User user) {
        this.post = post;
        this.commentContent = commentContent;
        this.user = user;
    }

    public void update(String commentContent) {
        this.commentContent = commentContent;
    }

}
