package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class SavePostRequestDto {
    private String title;
    private String content;

    //테스트용
    public SavePostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
