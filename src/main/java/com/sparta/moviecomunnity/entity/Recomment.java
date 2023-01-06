package com.sparta.moviecomunnity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Recomment extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @OneToMany(mappedBy = "recomment", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Heart> hearts;

    @Column
    private boolean available;

    public Recomment(String context, User user) {
        this.context = context;
        this.user = user;
        this.available = true;
        this.hearts = new ArrayList<>();
    }

    public void setComment(Comment comment) {
        if (this.comment != comment) {
            this.comment = comment;
            comment.addRecomment(this);
        }
    }

    public void addHeart(Heart heart) {
        if (!hearts.contains(heart)) {
            hearts.add(heart);
        }

        if (heart.getRecomment() != this) {
            heart.setRecomment(this);
        }
    }

    public void rewrite(String context) {
        this.context = context;
    }

    public void delete() {
        this.available = false;
    }

}
