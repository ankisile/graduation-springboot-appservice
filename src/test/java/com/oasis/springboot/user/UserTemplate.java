package com.oasis.springboot.user;

import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.user.SignUpRequestDto;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;

public class UserTemplate {
    public static final String EMAIL = "econg@example.com";
    public static final String PASSWORD = "econg1234!";
    public static final String NICKNAME = "nickname";

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static SignUpRequestDto makeTestSignUpRequestDtoWithImage() throws Exception {
        return new SignUpRequestDto(EMAIL, PASSWORD, NICKNAME, getProfileImage());
    }

    public static User makeTestUser() throws Exception {
        return User.builder()
                .email(EMAIL)
                .password(bCryptPasswordEncoder.encode(PASSWORD))
                .nickName(NICKNAME)
                .picture("imageUrl")
                .role(Role.USER).build();
    }

    public static MockMultipartFile getProfileImage() throws Exception {
        String fileName = "testImage";
        String contentType = "jpg";
        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("profileImage", fileName + "." + contentType, contentType, fileInputStream);
    }
}
