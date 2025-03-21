package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.AuthRequestDto;
import com.example.newsfeed.auth.dto.AuthResponseDto;
import com.example.newsfeed.auth.dto.LoginAuthRequestDto;
import com.example.newsfeed.base.config.PasswordEncoder;
import com.example.newsfeed.base.filter.JwtUtil;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void signup(AuthRequestDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일임");
        }

        String encode = passwordEncoder.encode(dto.getPassword());//인코드

        UserEntity user = new UserEntity(dto.getEmail(), encode, dto.getNickName(), dto.getName());
        userRepository.save(user);
    }

    //로그인
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginAuthRequestDto dto){
        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는 회원임"));

        if(passwordEncoder.matches(user.getPassword(), dto.getPassword())){
            throw new IllegalStateException("비밀번호 잘못됨");
        }

        String bearerJwt = jwtUtil.createToken(user.getUserId(), user.getEmail());
        return new AuthResponseDto(bearerJwt);
    }
}
