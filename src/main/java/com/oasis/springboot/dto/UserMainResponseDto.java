package com.oasis.springboot.dto;

import com.oasis.springboot.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMainResponseDto {
    private String email;
    private String nickName;
    private String picture;

    public UserMainResponseDto(User user) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.picture = user.getPicture();
    }
}
