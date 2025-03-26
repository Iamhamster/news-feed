package com.example.newsfeed.post.service;

import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시물 생성
    @Transactional
    public SavePostResponseDto savePost(Long userId, SavePostRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("사용자가 아닙니다."));
        PostEntity post = new PostEntity(dto.getTitle(), dto.getContent(), user, 0L);
        postRepository.save(post);
        return new SavePostResponseDto(post.getUser().getUserId(), post.getId(), post.getTitle(), post.getLikeCount(), post.getContent(), post.getCreatedAt());
    }

    //게시물 다건 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllPost(Pageable pageable, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate!=null) startDateTime = LocalDateTime.parse(startDate, formatter);//형식 변환
        if(endDate!=null) endDateTime = LocalDateTime.parse(endDate, formatter);//형식 변환

        Page<PostEntity> posts;

        /*Lv5. 기간별 검색 기능*/
        if(startDateTime!=null && endDateTime!=null){ posts = postRepository.findByCreatedAtBetween(startDateTime, endDateTime, pageable); } //startDate ~ endDate 사이 검색
        else if(startDateTime!=null){ posts = postRepository.findByCreatedAtAfter(startDateTime, pageable); }//startDate부터 검색
        else if(endDateTime!=null){ posts = postRepository.findByCreatedAtBefore(endDateTime, pageable); }//endDate부터 검색
        else{ posts = postRepository.findAll(pageable); }//검색 지정이 없는 경우, 모두 출력

        return posts.map(post -> new PostResponseDto(
                    post.getUser().getNickName(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLikeCount(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
                )
        );
    }

    //게시물 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("조회할 게시물 없음"));
        return new PostResponseDto(post.getUser().getNickName(), post.getTitle(), post.getContent(), post.getLikeCount(), post.getCreatedAt(), post.getUpdatedAt());
    }

    //게시물 수정
    @Transactional
    public UpdatePostResponseDto updatePost(Long userId, Long postId, UpdatePostRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("사용자가 아닙니다."));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("수정할 게시물 없음"));
        post.updatePost(dto.getTitle(), dto.getContent());
        return new UpdatePostResponseDto(post.getTitle(), post.getContent(), post.getLikeCount(), post.getCreatedAt(), post.getUpdatedAt());
    }

    //게시물 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }





    /*todo: 팔로우 부분*/
    //팔로우 리스트
    public Page<PostResponseDto> followList(Pageable pageable, AuthUser user){
        UserEntity users = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );
        Page<PostEntity> posts = postRepository.findAllByFollow(users.getUserId(), pageable);
        return posts.map(post -> new PostResponseDto(
                        post.getUser().getNickName(),
                        post.getTitle(),
                        post.getContent(),
                        post.getLikeCount(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )
        );
    }
}
