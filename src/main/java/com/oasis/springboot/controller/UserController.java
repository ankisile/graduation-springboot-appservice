package com.oasis.springboot.controller;

import com.oasis.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "유저 회원가입", description = "")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "form-data 형식으로 전달 필요")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(
            @Schema(name = "RegisterDto",
                    description = "회원가입(json)",
                    required = true,
                    example = "{\n" +
                            "    \"email\":\"string@(이메일 주소)\",\n" +
                            "    \"password\":\"string@(비번)\",\n" +
                            "    \"nickName\":\"string@\"\n" +
                            "}"
            )
            @RequestPart(value = "key") String requestDto,

            @Parameter(name = "file", description = "사진(maxSize: 10MB)")
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(userService.signup(requestDto, file));
    }

}
