package com.example.newsfeed.like.service;

import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.like.dto.SaveLikeResponseDto;
import com.example.newsfeed.like.entity.LikeEntity;
import com.example.newsfeed.like.repository.LikeRepository;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private int sw=0;

    @Transactional
        public SaveLikeResponseDto saveLike(AuthUser user, Long postId) {
            UserEntity users = userRepository.findById(user.getId()).orElseThrow(
                    () -> new IllegalStateException("사용자가 없음")
            );
            PostEntity post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalStateException("게시글이 없음")
            );
            LikeEntity like = new LikeEntity(users, post);
            likeRepository.save(like);

            if(sw==0){
                post.updateLikePost(post.getLikeCount()+1);
            }
            else{
                post.updateLikePost(post.getLikeCount());
            }
            sw++;

            return new SaveLikeResponseDto(post.getLikeCount());
    }

    @Transactional
    public void deleteLike(AuthUser user) {
        sw=0;
        LikeEntity like = likeRepository.findById(user.getId())//?
                .orElseThrow(() -> new IllegalStateException(""));
        if(like.getPost().getUser().getUserId().equals(user.getId())){
            throw new IllegalStateException("본인이 작성한 게시물에는 좋아요를 할 수 없습니다.");
        }
        PostEntity post = postRepository.findById(like.getPost().getId())
                .orElseThrow(() -> new IllegalStateException("좋아요할 게시물이 없습니다."));
        post.updateLikePost(post.getLikeCount()-1);
        likeRepository.deleteById(like.getId());
    }
}
