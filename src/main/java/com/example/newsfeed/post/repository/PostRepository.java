package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    //날짜&시간 범위 검색 기능
    Page<PostEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<PostEntity> findByCreatedAtAfter(LocalDateTime startDate, Pageable pageable);
    Page<PostEntity> findByCreatedAtBefore( LocalDateTime endDate, Pageable pageable);

    /*todo: 팔로우 부분*/
    //뉴스피드에 내가 팔로우한 사람의 최신 post을 최신순으로 볼 수 있습니다.
    @Query("SELECT p FROM PostEntity p " +
           "JOIN FollowEntity f ON p.user.userId = f.user2.userId " +   //팔로우 당한 사람(상대방)
           "WHERE f.user.userId = :userId " +       //팔로우한 사람(나)
           "ORDER BY p.createdAt DESC")
    Page<PostEntity> findAllByFollow(@Param("userId") Long userId, Pageable pageable);
}
