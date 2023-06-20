package com.oasis.springboot.user;

import com.oasis.springboot.common.exception.ExistUserException;
import com.oasis.springboot.common.exception.InvalidateUserException;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.user.*;
import com.oasis.springboot.service.AuthService;
import com.oasis.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    S3Uploader s3Uploader;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${user.default.image}")
    private String defaultImg;


    @Test
    void 회원가입() throws Exception {
        //given
        SignUpRequestDto requestDto = makeTestSignUpRequestDtoWithImage();

        //when
        Long id = userService.signup(requestDto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidateUserException());

        //then
        assertThat(user.getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(user.getNickName()).isEqualTo(requestDto.getNickName());

        //teardown
        String str = user.getPicture();
        String path = str.substring(62, str.length());
        s3Uploader.delete(path);
    }

    @Test
    void 회원가입_실패_이메일중복() throws Exception {
        //given
        User user = makeTestUser();
        SignUpRequestDto requestDto = makeTestSignUpRequestDtoWithImage();

        //when
        userRepository.save(user);

        //then
        assertThatThrownBy(() -> userService.signup(requestDto)).isInstanceOf(ExistUserException.class);
    }

    @Test
    void 로그인_테스트() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);
        LoginDto loginDto = LoginDto.builder().
                email(user.getEmail()).
                password(PASSWORD).
                fcmToken("token").
                build();

        //when
        TokenResponseDto responseDto = authService.login(loginDto);

        //then
        assertThat(responseDto.getToken()).isNotNull();
    }

    @Test
    void fcm_토큰_업데이트() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);
        LoginDto loginDto = LoginDto.builder().
                email(user.getEmail()).
                password(PASSWORD).
                fcmToken("token").
                build();

        //when
        authService.updateFcmToken(loginDto);

        //then
        assertThat(user.getFcmToken()).isEqualTo("token");

    }

    @Test
    void 현재_사용자() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);

        //when
        User userResult = userService.findUser();

        //then
        assertThat(user.getEmail()).isEqualTo(userResult.getEmail());
    }

    @Test
    void 유저_이메일로_찾기() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);

        //when
        User userResult = userService.findByEmail(user.getEmail());

        //then
        assertThat(user.getEmail()).isEqualTo(userResult.getEmail());
    }

    @Test
    void 현재유저_Id_찾기() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);

        //when
        Long id = userService.findUserId();

        //then
        assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    void 유저정보업데이트() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);
        UpdateUserRequestDto requestDto = new UpdateUserRequestDto("hanrry", getProfileImage(), "true");

        //when
        userService.updateUserInfo(requestDto);

        //then
        assertThat(user.getNickName()).isEqualTo("hanrry");

        //teardown
        String str = user.getPicture();
        String path = str.substring(62, str.length());
        s3Uploader.delete(path);
    }


    @Test
    void 비밀번호변경() throws Exception {
        //given
        User user = makeTestUser();
        userRepository.save(user);
        PasswordDto requestDto = new PasswordDto(PASSWORD, "newPassword");

        //when
        userService.updatePassword(requestDto);

        //then
        assertThat(user.getPassword()).isNotEqualTo(passwordEncoder.encode(PASSWORD));
    }
}
