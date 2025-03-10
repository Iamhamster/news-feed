package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시물 생성
    @Transactional
    public SavePostResponseDto savePost(SavePostRequestDto dto) {
        PostEntity post = new PostEntity(dto.getTitle(), dto.getContent());
        postRepository.save(post);
        return new SavePostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
    }

    //게시물 다건 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPost() {
        List<PostEntity> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();
        for(PostEntity post: posts){
            dtos.add(new PostResponseDto(post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt()));
        }
        return dtos;
    }

    //게시물 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("조회할 게시물 없음"));
        return new PostResponseDto(post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }

    //게시물 수정
    @Transactional
    public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto dto) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("수정할 게시물 없음"));
        post.updatePost(dto.getTitle(), dto.getContent());
        return new UpdatePostResponseDto(post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }

    //게시물 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
