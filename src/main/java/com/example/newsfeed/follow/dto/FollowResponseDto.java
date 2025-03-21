package com.example.newsfeed.follow.dto;

import com.example.newsfeed.post.dto.PostResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FollowResponseDto {
    private final String nickName;
    private final List<PostResponseDto> postList;
}
