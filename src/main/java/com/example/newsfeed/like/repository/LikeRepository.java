package com.example.newsfeed.like.repository;

import com.example.newsfeed.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}
