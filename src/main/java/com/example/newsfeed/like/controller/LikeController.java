package com.example.newsfeed.like.controller;

import com.example.newsfeed.base.config.Auth;
import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.like.dto.SaveLikeResponseDto;
import com.example.newsfeed.like.service.LikeService;
import com.example.newsfeed.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/v1/likes/{postId}")
    public ResponseEntity<SaveLikeResponseDto> saveLike(@Auth AuthUser user, @PathVariable Long postId){
        return ResponseEntity.ok(likeService.saveLike(user, postId));
    }

    @DeleteMapping("/v1/likes/{postId}")
    public void deleteLike(@Auth AuthUser user){
        likeService.deleteLike(user);
    }
}
