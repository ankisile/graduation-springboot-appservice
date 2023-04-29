package com.oasis.springboot.domain.user;

import com.oasis.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String fcmToken;

    @Builder
    public User (String nickName, String email, String password, String picture, Role role) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }

    public void modifyNickName(String nickName){
        this.nickName = nickName;
    }

    public void modifyPicture(String picture){
        this.picture = picture;
    }

    public void modifyPassword(String password) { this.password = password; }

    public void updateFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
