package com.example.newsfeed.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveLikeResponseDto {
    private final Long likeCount;
}
