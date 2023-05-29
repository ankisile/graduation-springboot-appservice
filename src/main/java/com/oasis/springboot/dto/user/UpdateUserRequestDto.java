package com.oasis.springboot.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequestDto {

    @NotBlank(message = "닉네임이 없습니다.")
    private String nickName;

    @NotBlank(message = "파일이 없습니다.")
    private MultipartFile file;

}
