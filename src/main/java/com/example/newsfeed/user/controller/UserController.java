package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.dto.*;
import com.example.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    //인증인가
    @PutMapping("")
    public void update(@RequestBody UserRequestDto dto){
        userService.update(dto);
    }

    //비밀번호 수정
    @PatchMapping("/v1/auths/password")
    public ResponseEntity<UpdatePasswordResponseDto> updatePasswordUser(@PathVariable Long userId, @RequestBody UpdatePasswordUserRequestDto dto){
        return ResponseEntity.ok(userService.updatePasswordUser(userId, dto));
    }

    //프로필 생성
    @PostMapping("/v1/users")
    public ResponseEntity<SaveUserResponseDto> saveUser(@RequestBody SaveUserRequestDto dto){
        return ResponseEntity.ok(userService.saveUser(dto));
    }

    //프로필 조회(단건)
    @GetMapping("/v1/users/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findUser(userId));
    }

    //프로필 수정
    @PutMapping("/v1/users/{userId}")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }
}
