package com.oasis.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private String currentPW;
    private String newPW;
}
