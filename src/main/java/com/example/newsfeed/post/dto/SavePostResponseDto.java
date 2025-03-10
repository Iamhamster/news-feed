package com.example.newsfeed.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SavePostResponseDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
}
