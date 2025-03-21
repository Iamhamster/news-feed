package com.example.newsfeed.follow.entity;

import com.example.newsfeed.base.entity.BaseEntity;
import com.example.newsfeed.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "follows")
public class FollowEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followId", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId2", nullable = false)
    private UserEntity user2;

    public FollowEntity(UserEntity user, UserEntity user2) {
        this.user = user;
        this.user2 = user2;
    }
}