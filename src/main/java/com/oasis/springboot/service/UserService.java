package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.common.exception.EmptyAuthenticationException;
import com.oasis.springboot.common.exception.ExistUserException;
import com.oasis.springboot.common.exception.InvalidateUserException;
import com.oasis.springboot.common.exception.NotMatchPasswordException;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.PasswordDto;
import com.oasis.springboot.dto.RegisterDto;
import com.oasis.springboot.dto.UserMainResponseDto;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.common.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${user.default.image}")
    private String defaultImg;

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

        String s3Url = defaultImg;
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
        String email = findCurrentUserEmail();

        return userRepository.findByEmail(email)
                .map(user -> new UserMainResponseDto(user))
                .orElseThrow(InvalidateUserException::new);
    }

    public User findByEmail(){
        String email = findCurrentUserEmail();
        return userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);

    }

    public Long findUserId(){
        String email = findCurrentUserEmail();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);
        return user.getId();
    }

    @Transactional
    public String updateUserInfo(String nickName, Boolean isChange, MultipartFile file) {
        User user = findByEmail();
        System.out.print(nickName);
        if(nickName != null)
            user.modifyNickName(nickName);

        if(isChange) {
            String s3Url = defaultImg;
            try{
                if (file != null) {
                    s3Url = s3Uploader.upload(file, "static");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.modifyPicture(s3Url);
        }
        return "success";
    }

    @Transactional
    public String updatePassword(PasswordDto passwordDto){
        User user = findByEmail();
        if(!passwordEncoder.matches(passwordDto.getCurrentPW(), user.getPassword()))
            throw new NotMatchPasswordException();
        String newPw = passwordEncoder.encode(passwordDto.getNewPW());
        user.modifyPassword(newPw);
        return "success";
    }


    private String findCurrentUserEmail(){
        String email = SecurityUtil.getCurrentEmail()
                .orElseThrow(EmptyAuthenticationException::new);
        return email;
    }

}
