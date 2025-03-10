package com.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordUserRequestDto {
    private String oldPassword;
    private String newPassword;
}
