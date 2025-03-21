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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profile = "비어있는 profile 입니다.";

    @Column(nullable = false)
    private int followerUsers = 0;

    @Column(nullable = false)
    private int followingUsers = 0;

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

    //팔로잉 & 팔로워
    public void setFollowerUsers(){
        followerUsers++;
    }
    public void setFollowingUsers(){
        followingUsers++;
    }
}
