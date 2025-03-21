package com.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class SaveUserRequestDto {
    private String profile;

    //테스트용
    public SaveUserRequestDto(String profile) {
        this.profile = profile;
    }
}
