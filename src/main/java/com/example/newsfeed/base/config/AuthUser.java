package com.example.newsfeed.base.config;

import lombok.Getter;

//jwt에서 뽑아온 사용자 정보
//사용하기 편하게
@Getter
public class AuthUser {
    private Long id;
    private String email;

    public AuthUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
