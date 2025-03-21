package com.example.newsfeed.comment.entity;

import com.example.newsfeed.base.entity.BaseEntity;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class CommentEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public CommentEntity(String comment, PostEntity post, UserEntity user) {
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
