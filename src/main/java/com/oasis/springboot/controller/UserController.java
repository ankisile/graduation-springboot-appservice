package com.oasis.springboot.controller;

import com.oasis.springboot.dto.RegisterDto;
import com.oasis.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

//    @PostMapping("/signup")
//    public ResponseEntity<? extends BasicResponse> signup(@Valid @RequestBody UserSaveRequestDto requestDto) {
//        return ResponseEntity.ok(new CommonResponse<>(userService.signup(requestDto)));
//    }

}
