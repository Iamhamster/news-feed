package com.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {
    private String profile;

    //테스트용
    public UpdateUserRequestDto(String profile) {
        this.profile = profile;
    }
}
