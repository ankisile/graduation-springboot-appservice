package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.*;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.user.PasswordDto;
import com.oasis.springboot.dto.user.SignUpRequestDto;
import com.oasis.springboot.dto.user.UpdateUserRequestDto;
import com.oasis.springboot.dto.user.UserMainResponseDto;
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

    @Transactional
    public long signup(SignUpRequestDto signUpRequestDto){
        String email = signUpRequestDto.getEmail();
        checkDuplicateEmail(email);

        String fileUrl = s3Uploader.getFileS3Url(signUpRequestDto.getFile(), "static");

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .nickName(signUpRequestDto.getNickName())
                .picture(fileUrl)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public String updateUserInfo(UpdateUserRequestDto requestDto) {
        User user = findUser();

        user.modifyNickName(requestDto.getNickName());

        String fileUrl = s3Uploader.getFileS3Url(requestDto.getFile(), "static");

        user.modifyPicture(fileUrl);

        return "success";
    }

    @Transactional
    public String updatePassword(PasswordDto passwordDto){
        User user = findUser();
        if(!passwordEncoder.matches(passwordDto.getCurrentPW(), user.getPassword()))
            throw new NotMatchPasswordException();
        String newPw = passwordEncoder.encode(passwordDto.getNewPW());
        user.modifyPassword(newPw);
        return "success";
    }

    private void checkDuplicateEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new ExistUserException();
        }
    }

    private String findCurrentUserEmail(){
        String email = SecurityUtil.getCurrentEmail()
                .orElseThrow(EmptyAuthenticationException::new);
        return email;
    }

    public UserMainResponseDto findUserInfo() {
        String email = findCurrentUserEmail();

        return userRepository.findByEmail(email)
                .map(user -> new UserMainResponseDto(user))
                .orElseThrow(InvalidateUserException::new);
    }

    public User findUser(){
        String email = findCurrentUserEmail();
        return userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);

    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);
    }


    public Long findUserId(){
        String email = findCurrentUserEmail();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(InvalidateUserException::new);
        return user.getId();
    }
}
