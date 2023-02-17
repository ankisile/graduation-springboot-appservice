package com.oasis.springboot.controller;

import com.oasis.springboot.dto.LoginDto;
import com.oasis.springboot.dto.TokenResponseDto;
import com.oasis.springboot.common.jwt.JwtFilter;
import com.oasis.springboot.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "유저 로그인", description = "")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        TokenResponseDto tokenResponseDto = authService.login(loginDto);

        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        // 2. Response Body에 token 값을 넣어준다.
        return new ResponseEntity<>(tokenResponseDto, httpHeaders, HttpStatus.OK);
    }

}
