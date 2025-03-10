package com.example.newsfeed.user.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveUserResponseDto {
    private final String profile;
}
