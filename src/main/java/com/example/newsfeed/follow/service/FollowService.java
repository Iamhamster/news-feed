package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.dto.FollowRequestDto;
import com.example.newsfeed.follow.dto.FollowResponseDto;
import com.example.newsfeed.follow.dto.SaveFollowRequestDto;
import com.example.newsfeed.follow.entity.FollowEntity;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //팔로우
    @Transactional
    public void saveFollow(Long userId, SaveFollowRequestDto dto) {
        UserEntity user = userRepository.findById(userId)//자신
                .orElseThrow(() -> new IllegalStateException("사용자가 없습니다."));
        UserEntity user2 = userRepository.findByNickName(dto.getNickName())//팔로우 타겟
                .orElseThrow(() -> new IllegalStateException("사용자가 없습니다."));

        //자기 자신 팔로우 금지
        if (user.getUserId().equals(user2.getUserId()))
            throw new IllegalStateException("본인을 팔로우할 수 없습니다.");
        //팔로우 상태 검증
        Optional<FollowEntity> existFollowState = followRepository.findByUserAndUser2(user, user2);
        if (existFollowState.isPresent())
            throw new IllegalStateException("이미 팔로우된 상태입니다.");//값이 있다면

        FollowEntity follow = new FollowEntity(user, user2);//나 = user      친구추가 = user2
        followRepository.save(follow);
        user.setFollowingUsers();
        user2.setFollowerUsers();
    }

    //추가된 친구의 게시글 모아보기?
    @Transactional(readOnly = true)
    public FollowResponseDto findFollowAllPost(Long id, FollowRequestDto dto) {
        UserEntity user = userRepository.findByNickName(dto.getNickName())
                .orElseThrow(() -> new IllegalStateException("사용자가 없습니다."));
        List<PostEntity> posts = postRepository.findAll();
        List<PostResponseDto> postList = new ArrayList<>();
        for(PostEntity post: posts){
            postList.add(new PostResponseDto(post.getUser().getNickName(), post.getTitle(), post.getContent(), post.getLikeCount(), post.getCreatedAt(), post.getUpdatedAt()));
        }
        return new FollowResponseDto(dto.getNickName(), postList);
    }
}
