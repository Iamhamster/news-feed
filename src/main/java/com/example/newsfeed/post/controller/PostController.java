package com.example.newsfeed.post.controller;

import com.example.newsfeed.base.config.Auth;
import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<SavePostResponseDto> savePost(@Auth AuthUser authUser, @RequestBody SavePostRequestDto dto){
        return ResponseEntity.ok(postService.savePost(authUser.getId(), dto));
    }

    //게시물 다건 조회
    /*Lv2. 뉴스피드 조회 기능(내림차순 정렬, 10개씩 페이지네이션)*/
    @GetMapping("/v1/posts/create")
    public ResponseEntity<List<PostResponseDto>> findAllPostCreatedAt(
            @PageableDefault(page=0, size = 10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,//10개씩     createdAt기준 DESC정렬
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ){
        return ResponseEntity.ok(postService.findAllPost(pageable, startDate, endDate).getContent());
    }

    //게시물 다건 조회
    @GetMapping("/v1/posts/update")
    public ResponseEntity<List<PostResponseDto>> findAllPostUpdatedAt(
            @PageableDefault(page=0, size = 10, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,//10개씩     updatedAt기준 DESC정렬
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ){
        return ResponseEntity.ok(postService.findAllPost(pageable, startDate, endDate).getContent());
    }

    //게시물 단건 조회
    @GetMapping("/v1/posts/{postId}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.findPost(postId));
    }

    //게시물 수정
    @PutMapping("/v1/posts/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody UpdatePostRequestDto dto){
        return ResponseEntity.ok(postService.updatePost(authUser.getId(), postId, dto));
    }

    //게시물 삭제
    @DeleteMapping("/v1/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }




    /*todo: 팔로우 부분*/
    //팔로우 리스트
    @GetMapping("/v1/follow/posts")
    public ResponseEntity<List<PostResponseDto>> followList(
            @PageableDefault(page=0, size = 10, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,//updatedAt기준 DESC정렬
            @Auth AuthUser authUser
    ){
        return ResponseEntity.ok(postService.followList(pageable, authUser).getContent());
    }
}
