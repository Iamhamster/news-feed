package com.example.newsfeed.follow.repository;

import com.example.newsfeed.follow.entity.FollowEntity;
import com.example.newsfeed.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByUserAndUser2(UserEntity user, UserEntity user2);
}
