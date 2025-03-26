package com.example.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class SaveFollowRequestDto {
    private String nickName;

    public SaveFollowRequestDto(String nickName) {
        this.nickName = nickName;
    }
}
