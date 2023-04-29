package com.oasis.springboot.template;

import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.SignUpRequestDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;

public class UserTemplate {
    public static final String EMAIL = "econg@example.com";
    public static final String PASSWORD = "econg1234!";
    public static final String NICKNAME = "nickname";

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static SignUpRequestDto makeTestSignUpRequestDto() throws Exception {
        return new SignUpRequestDto(EMAIL, PASSWORD, NICKNAME, null);
    }

    public static User makeTestUser() throws Exception {
        return User.builder()
                .email(EMAIL)
                .password(bCryptPasswordEncoder.encode(PASSWORD))
                .nickName(NICKNAME)
                .picture("imageUrl")
                .role(Role.USER).build();
    }

}
