package com.example.newsfeed.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {
    private final String bearerJwt;
}
