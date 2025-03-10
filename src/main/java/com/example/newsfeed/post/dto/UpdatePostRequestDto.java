package com.example.newsfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostRequestDto {
    private String title;
    private String content;
}
