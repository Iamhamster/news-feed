package com.example.newsfeed.follow.serviceTest;

import com.example.newsfeed.follow.dto.SaveFollowRequestDto;
import com.example.newsfeed.follow.entity.FollowEntity;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    @Test
    void 팔로우_성공() {
        //given
        Long userId = 1L;
        String targetNickName = "타겟닉네임";
        SaveFollowRequestDto requestDto = new SaveFollowRequestDto(targetNickName);
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        UserEntity targetUser = new UserEntity();
        targetUser.setNickName(targetNickName);

        //when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByNickName(targetNickName)).thenReturn(Optional.of(targetUser));
        when(followRepository.findByUserAndUser2(user, targetUser)).thenReturn(Optional.empty());

        followService.saveFollow(userId, requestDto);

        //then
        verify(followRepository, times(1)).save(any(FollowEntity.class));  // 팔로우 정보가 저장되는지 확인
        verify(userRepository, times(1)).findById(userId);  // userRepository에서 사용자 정보 조회 확인
        verify(userRepository, times(1)).findByNickName(targetNickName);  // targetUser 조회 확인
    }

    @Test
    void 본인_팔로우_시도_실패() {
        //given
        Long userId = 1L;
        String targetNickName = "타겟닉네임"; // 본인 팔로우
        SaveFollowRequestDto requestDto = new SaveFollowRequestDto(targetNickName);

        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setNickName("user");

        //when
        //user와 targetUser가 동일한 경우
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByNickName(targetNickName)).thenReturn(Optional.of(user));

        //when&then ?
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            followService.saveFollow(userId, requestDto);
        });

        assertEquals("본인을 팔로우할 수 없습니다.", thrown.getMessage());
    }
}
