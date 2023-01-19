package com.oasis.springboot.service;

import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.RegisterDto;
import com.oasis.springboot.dto.UserMainResponseDto;
import com.oasis.springboot.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(RegisterDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getNickName())
                .picture(requestDto.getPicture())
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
