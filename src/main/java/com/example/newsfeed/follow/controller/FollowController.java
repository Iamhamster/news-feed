package com.example.newsfeed.follow.controller;

import com.example.newsfeed.base.config.Auth;
import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.follow.dto.FollowRequestDto;
import com.example.newsfeed.follow.dto.FollowResponseDto;
import com.example.newsfeed.follow.dto.SaveFollowRequestDto;
import com.example.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    //팔로우
    @PostMapping("/v1/follows")
    public void saveFollow(@Auth AuthUser user, @RequestBody SaveFollowRequestDto dto){
        followService.saveFollow(user.getId(), dto);
    }

    //추가된 친구의 게시글 모아보기
    @GetMapping("/v1/follows/{followId}/posts")
    public ResponseEntity<FollowResponseDto> findFollowAllPost(@Auth AuthUser user, @RequestBody FollowRequestDto dto){
        return ResponseEntity.ok(followService.findFollowAllPost(user.getId(), dto));
    }
}
