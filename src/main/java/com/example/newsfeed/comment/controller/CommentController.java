package com.example.newsfeed.comment.controller;

import com.example.newsfeed.base.config.Auth;
import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.comment.dto.*;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<SaveCommentResponseDto> saveComment(PostEntity post, @RequestBody SaveCommentRequestDto dto){
        return ResponseEntity.ok(commentService.saveComment(post.getId(), dto));
    }

    //댓글 다건 조회
    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAllComment(){
        return ResponseEntity.ok(commentService.findAllComment());
    }

    //댓글 단건 조회
    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> findComment(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.findComment(commentId));
    }

    //댓글 수정
    @PutMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@PathVariable Long commentId, @Auth AuthUser user, @RequestBody UpdateCommentRequestDto dto){
        return ResponseEntity.ok(commentService.updateComment(commentId, user, dto));
    }

    //댓글 삭제
    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @Auth AuthUser user){
        commentService.deleteComment(commentId, user);
    }
}
