package com.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordUserRequestDto {
    private String oldPassword;
    private String newPassword;

    //테스트용
    public UpdatePasswordUserRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
