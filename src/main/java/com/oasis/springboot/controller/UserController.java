package com.oasis.springboot.controller;

import com.oasis.springboot.common.exception.Exception;
import com.oasis.springboot.common.exception.ExistUserException;
import com.oasis.springboot.common.exception.FileUploadFailException;
import com.oasis.springboot.common.exception.NotMatchPasswordException;
import com.oasis.springboot.common.response.CommonResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.dto.PasswordDto;
import com.oasis.springboot.dto.SignUpRequestDto;
import com.oasis.springboot.dto.UserMainResponseDto;
import com.oasis.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Tag(name = "유저 회원가입", description = "")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @Operation(summary = "회원가입", description = "form-data 형식으로 전달 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "1002", description = "Exist User", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SingleResponse<Long> signup(@ModelAttribute @Valid SignUpRequestDto signUpRequestDto) {
        return responseService.getSingleResponse(userService.signup(signUpRequestDto));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "정보 불러오기")
    @GetMapping("/user")
    public SingleResponse<UserMainResponseDto> getUserInfo(){
        return responseService.getSingleResponse(userService.findUserInfo());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "정보 수정", description = "form-data 형식으로 전달 필요")
    @PatchMapping(value ="/user/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SingleResponse<String> updateUserInfo(
            @Schema(description = "유저정보 닉네임만(String)",
                    example = "nickName"
            )
            @RequestPart(value = "name", required = false) String nickName,
            @Schema(description = "사진 변경 여부. boolean인데 string으로 전달 바람 true false 이런식으로")
            @RequestPart(value = "change") String isChange,
            @Parameter(name = "file", description = "사진(maxSize: 10MB), 변경되지 않으면 null 기본이미지로 바뀔때는 boolean 값만 전달 사진은 null")
            @RequestPart(value = "file", required = false) MultipartFile file
    ){
        return responseService.getSingleResponse(userService.updateUserInfo(nickName, Boolean.parseBoolean(isChange), file));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "비밀번호 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "1003", description = "Password Not Match", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping(value ="/user/changepw")
    public SingleResponse<String> changeUserPassword(@RequestBody PasswordDto passwordDto){
        return responseService.getSingleResponse(userService.updatePassword(passwordDto));
    }

    @ExceptionHandler(ExistUserException.class)
    public CommonResponse existUserException(ExistUserException e){
        return responseService.getErrorResponse(Exception.EXIST_USER.getCode(), Exception.EXIST_USER.getMessage());
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public CommonResponse notMatchPasswordException(NotMatchPasswordException e){
        return responseService.getErrorResponse(Exception.NOT_MATCH_PASSWORD.getCode(), Exception.NOT_MATCH_PASSWORD.getMessage());
    }

    @ExceptionHandler(FileUploadFailException.class)
    public CommonResponse fileUploadFailException(FileUploadFailException e){
        return responseService.getErrorResponse(Exception.FILE_UPLOAD_FAIL.getCode(), Exception.FILE_UPLOAD_FAIL.getMessage());
    }


}
