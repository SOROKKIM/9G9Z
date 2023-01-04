package com.sparta.moviecomunnity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Recomment {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    public Recomment(String context, User user, Comment comment) {
        this.context = context;
        this.user = user;
        this.comment = comment;
        this.hearts = new ArrayList<>();
    }

    public void rewrite(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", context='" + context + '\'' +
                ", user=" + user.getUsername() +
                ", board=" + comment.getId() +
                " }";
    }
}
