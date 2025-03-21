package com.example.newsfeed.user.serviceTest;

import com.example.newsfeed.base.config.PasswordEncoder;
import com.example.newsfeed.user.dto.*;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import com.example.newsfeed.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    //비밀번호 수정
    @Test
    void 비밀번호를_수정할_수_있다(){
        //given
        Long userId = 1L;
        String password = "Test1234!23";
        String newPassword = "Test123!123";
        UserEntity user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
        ReflectionTestUtils.setField(user, "userId", userId);
        ReflectionTestUtils.setField(user, "password", password);
        UpdatePasswordUserRequestDto dto = new UpdatePasswordUserRequestDto(
                password,
                newPassword
        );

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(passwordEncoder.encode(anyString())).willReturn(newPassword); //passwordEncoder를 서비스에서 사용 안했음
        userService.updatePasswordUser(user.getUserId(), dto);

        //when
        UserEntity updateUser = userService.getUserEntity(userId);

        //then
        assertEquals(newPassword, updateUser.getPassword());
    }



    //프로필 생성
    @Test
    void 프로필을_생성할_수_있다(){
        //given
        Long userId = 1L;
        UserEntity user = new UserEntity("프로필");
        ReflectionTestUtils.setField(user, "userId", userId);
        SaveUserRequestDto dto = new SaveUserRequestDto("프로필");

        //when
        SaveUserResponseDto saveUserProfile = userService.saveUser(dto);

        //then
        assertEquals(user.getProfile(), saveUserProfile.getProfile());
    }



    //프로필 조회(단건)
    @Test
    void 프로필을_조회할_수_있다(){
        //given
        Long userId = 1L;
        UserEntity user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
        ReflectionTestUtils.setField(user, "userId", userId);
        ReflectionTestUtils.setField(user, "profile", "프로필");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //when
        UserResponseDto dto = userService.findUser(userId);

        //then
        assertEquals("프로필", dto.getProfile());
    }



    //프로필 수정
    @Test
    void 프로필을_수정할_수_있다(){
        //given
        Long userId = 1L;
        UserEntity user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
        ReflectionTestUtils.setField(user, "userId", userId);
        ReflectionTestUtils.setField(user, "profile", "프로필");
        UpdateUserRequestDto dto = new UpdateUserRequestDto("프로필2");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //when
        UpdateUserResponseDto result = userService.updateUser(userId, dto);

        //then
        assertEquals(userId, user.getUserId());
        assertEquals("프로필2", result.getProfile());
    }

}
