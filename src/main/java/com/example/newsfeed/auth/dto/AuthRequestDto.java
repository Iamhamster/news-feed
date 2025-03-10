package com.example.newsfeed.auth.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String email;
    private String password;
    private String nickName;
    private String name;
}
