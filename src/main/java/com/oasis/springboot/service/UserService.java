package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.common.exception.EmptyAuthenticationException;
import com.oasis.springboot.common.exception.ExistUserException;
import com.oasis.springboot.common.exception.InvalidateUserException;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.RegisterDto;
import com.oasis.springboot.dto.UserMainResponseDto;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.common.jwt.SecurityUtil;
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
            throw new ExistUserException();
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
                .orElseThrow(InvalidateUserException::new);
    }

    public UserMainResponseDto findUserInfo() {
        String email = findUserEmail();

        return userRepository.findByEmail(email)
                .map(user -> new UserMainResponseDto(user))
                .orElseThrow(InvalidateUserException::new);
    }

    public User findByEmail(){
        String email = findUserEmail();
        return userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);

    }

    public Long findUserId(){
        String email = findUserEmail();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);
        return user.getId();
    }

    @Transactional
    public String updateUserInfo(String nickName, MultipartFile file){
        User user = findByEmail();
        System.out.print(nickName);
        if(nickName != null)
            user.modifyNickName(nickName);

        try {
            if(file != null){
                String s3Url = s3Uploader.upload(file, "static");
                user.modifyPicture(s3Url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private String findUserEmail(){
        String email = SecurityUtil.getCurrentEmail()
                .orElseThrow(EmptyAuthenticationException::new);
        return email;
    }

}
