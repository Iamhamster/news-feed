package com.example.newsfeed.user.service;

import com.example.newsfeed.base.config.PasswordEncoder;
import com.example.newsfeed.user.dto.*;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("비밀번호를 변경할 유저가 아닙니다.")
        );
    }

    //비밀번호 수정
    @Transactional
    public UpdatePasswordResponseDto updatePasswordUser(Long userId, UpdatePasswordUserRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("비밀번호를 변경할 유저가 아닙니다."));
        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())){
            throw new IllegalStateException("현재 비밀번호가 일치하지 않습니다.");
        }
        if(dto.getOldPassword().equals(dto.getNewPassword())){
            throw new IllegalStateException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
        }
        String encode = passwordEncoder.encode(dto.getNewPassword());

        user.updatePassword(encode);
        return new UpdatePasswordResponseDto(user.getPassword());
    }

    //프로필 생성
    @Transactional
    public SaveUserResponseDto saveUser(SaveUserRequestDto dto) {
        UserEntity user = new UserEntity(
                dto.getProfile()
        );
        userRepository.save(user);
        return new SaveUserResponseDto(
                user.getProfile()
        );
    }

    //프로필 조회(단건)
    @Transactional(readOnly = true)
    public UserResponseDto findUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("보여줄 유저 없음"));
        return new UserResponseDto(user.getProfile());
    }

    //프로필 수정
    @Transactional
    public UpdateUserResponseDto updateUser(Long userId, UpdateUserRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("수정할 유저 없음"));
        user.updateUser(dto.getProfile());
        return new UpdateUserResponseDto(user.getProfile());
    }
}
