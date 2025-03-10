package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.AuthRequestDto;
import com.example.newsfeed.auth.dto.AuthResponseDto;
import com.example.newsfeed.base.config.JwtUtil;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import com.example.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Transactional
    public void signup(AuthRequestDto dto){
        if(userRepository.existByEmail(dto.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일임");
        }

        UserEntity user = new UserEntity(dto.getEmail(), dto.getPassword(), dto.getNickName(), dto.getName());
        UserEntity savedUser = userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(AuthRequestDto dto){
        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("없는 회원임"));
        String password = dto.getPassword();
        if(password.equals(user.getPassword())){
            throw new IllegalStateException("비밀번호 잘못됨");
        }

        String bearerJwt = jwtUtil.createToken(user.getUserId(), user.getEmail());
        return new AuthResponseDto(bearerJwt);
    }
}
