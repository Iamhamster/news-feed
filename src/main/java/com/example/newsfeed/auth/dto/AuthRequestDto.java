package com.example.newsfeed.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;

@Getter
public class AuthRequestDto {
    //@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$")
    private String password;
    private String nickName;
    private String name;
}
