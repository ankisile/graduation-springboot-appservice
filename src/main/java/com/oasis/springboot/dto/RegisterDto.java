package com.oasis.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    private String email;
    private String password;
    private String nickName;
    private String picture;
}
