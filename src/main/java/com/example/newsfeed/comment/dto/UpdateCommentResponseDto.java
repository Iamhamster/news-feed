package com.example.newsfeed.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateCommentResponseDto {
    private final String nickName;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
