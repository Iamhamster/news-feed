package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.dto.AuthRequestDto;
import com.example.newsfeed.auth.dto.AuthResponseDto;
import com.example.newsfeed.auth.dto.LoginAuthRequestDto;
import com.example.newsfeed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    //회원가입
    @PostMapping("/v1/auth/signup")
    public void signup(@RequestBody AuthRequestDto dto){
        authService.signup(dto);
    }

    //로그인
    @PostMapping("/v1/auth/login")
    public AuthResponseDto login(@RequestBody LoginAuthRequestDto dto){
        return authService.login(dto);
    }
}
