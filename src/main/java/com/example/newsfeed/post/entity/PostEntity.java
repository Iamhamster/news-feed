package com.example.newsfeed.post.entity;

import com.example.newsfeed.base.entity.BaseEntity;
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

    public PostEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
