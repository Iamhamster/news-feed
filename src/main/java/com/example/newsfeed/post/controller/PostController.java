package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    //게시물 생성
    @PostMapping("/v1/posts")
    public ResponseEntity<SavePostResponseDto> savePost(@RequestBody SavePostRequestDto dto){
        return ResponseEntity.ok(postService.savePost(dto));
    }

    //게시물 다건 조회
    @GetMapping("/v1/posts")
    public ResponseEntity<List<PostResponseDto>> findAllPost(){
        return ResponseEntity.ok(postService.findAllPost());
    }

    //게시물 단건 조회
    @GetMapping("/v1/posts/{postId}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.findPost(postId));
    }

    //게시물 수정
    @PutMapping("/v1/posts/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequestDto dto){
        return ResponseEntity.ok(postService.updatePost(postId, dto));
    }

    //게시물 삭제
    @DeleteMapping("/v1/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}
