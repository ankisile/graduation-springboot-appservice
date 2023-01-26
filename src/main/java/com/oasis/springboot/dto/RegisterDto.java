package com.oasis.springboot.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterDto {

    private String email;
    private String password;
    private String nickName;
}
