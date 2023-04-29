package com.oasis.springboot.template;

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

//    public static MockMultipartFile getTestUserImage() throws Exception {
//        String fileName = "testImage";
//        String contentType = "png";
//        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        return new MockMultipartFile("userImage", fileName + "." + contentType, contentType, fileInputStream);
//    }

}
