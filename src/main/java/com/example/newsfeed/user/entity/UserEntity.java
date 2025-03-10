package com.example.newsfeed.user.entity;

import com.example.newsfeed.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String profile;

    public UserEntity(String email, String password, String nickName, String name) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
    }

    public UserEntity(String profile) {
        this.profile = profile;
    }

    public void updateUser(String profile) {
        this.profile = profile;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
