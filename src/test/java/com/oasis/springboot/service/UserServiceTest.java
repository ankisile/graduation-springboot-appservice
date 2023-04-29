package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.InvalidateUserException;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.SignUpRequestDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static com.oasis.springboot.template.UserTemplate.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@WithMockUser(username = "email", password = "password")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    S3Uploader s3Uploader;

    @Test
    void 회원가입() throws Exception {
        //given
        SignUpRequestDto requestDto = makeTestSignUpRequestDto();

        //when
        Long id = userService.signup(requestDto);
        User user = userRepository.findById(id)
                .orElseThrow(()->new InvalidateUserException());

        //then
        assertThat(user.getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(user.getNickName()).isEqualTo(requestDto.getNickName());

        //teardown
        s3Uploader.delete(user.getPicture());
    }


}
