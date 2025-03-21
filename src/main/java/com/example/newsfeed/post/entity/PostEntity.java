package com.example.newsfeed.post.entity;

import com.example.newsfeed.base.entity.BaseEntity;
import com.example.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "posts")
public class PostEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    private Long likeCount;

    public PostEntity(String title, String content, UserEntity user, Long likeCount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.likeCount = likeCount;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateLikePost(Long likeCount) {
        this.likeCount = likeCount;
    }
}
