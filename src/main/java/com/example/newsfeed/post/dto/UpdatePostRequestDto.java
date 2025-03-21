package com.example.newsfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostRequestDto {
    private String title;
    private String content;
    private Long likeCount;

    //테스트용
    public UpdatePostRequestDto(String title, String content, Long likeCount) {
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
    }
}
