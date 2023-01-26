package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.RegisterDto;
import com.oasis.springboot.dto.UserMainResponseDto;
import com.oasis.springboot.handler.S3Uploader;
import com.oasis.springboot.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    //에러 핸들러 처리 필요
    @Transactional
    public String signup(String string, MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        RegisterDto requestDto = null;
        try {
            requestDto = mapper.readValue(string, RegisterDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (userRepository.findByEmail(requestDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        String s3Url = "https://graduationplantbucket.s3.ap-northeast-2.amazonaws.com/static/flower-pot.png";
        try {
            if(file != null)
                s3Url = s3Uploader.upload(file, "static");
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getNickName())
                .picture(s3Url)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "Success";
    }

    public UserMainResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserMainResponseDto(user))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 user 입니다. id=" + id));
    }

    public UserMainResponseDto findUserInfo() {
        String email = SecurityUtil.getCurrentEmail().orElseThrow(() ->
                new RuntimeException("Security Context에 인증 정보가 없습니다."));

        return userRepository.findByEmail(email)
                .map(user -> new UserMainResponseDto(user))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 user 입니다. email=" + email));
    }

    public User findByEmail(){
        String email = SecurityUtil.getCurrentEmail().orElseThrow(() ->
                new RuntimeException("Security Context에 인증 정보가 없습니다."));
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않는 user 입니다. email=" + email));

    }

    public Long findUserId(){
        String email = SecurityUtil.getCurrentEmail().orElseThrow(() ->
                new RuntimeException("Security Context에 인증 정보가 없습니다."));
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않는 user 입니다. email=" + email));
        return user.getId();
    }

}
