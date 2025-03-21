package com.example.newsfeed.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final String nickName;
    private final String title;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
