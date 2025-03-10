package com.example.newsfeed.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdatePasswordResponseDto {
    private final String password;
}
