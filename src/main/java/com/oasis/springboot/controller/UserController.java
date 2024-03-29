package com.oasis.springboot.controller;

import com.oasis.springboot.common.exception.ErrorCode;
import com.oasis.springboot.common.exception.ExistUserException;
import com.oasis.springboot.common.exception.FileUploadFailException;
import com.oasis.springboot.common.exception.NotMatchPasswordException;
import com.oasis.springboot.common.response.CommonResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.dto.user.PasswordDto;
import com.oasis.springboot.dto.user.SignUpRequestDto;
import com.oasis.springboot.dto.user.UpdateUserRequestDto;
import com.oasis.springboot.dto.user.UserMainResponseDto;
import com.oasis.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "유저", description = "")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @Operation(summary = "회원가입", description = "form-data 형식으로 전달 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "1002", description = "Exist User", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "1006", description = "File Upload Fail", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SingleResponse<Long> signup(@ModelAttribute @Valid SignUpRequestDto signUpRequestDto) {
        return responseService.getSingleResponse(userService.signup(signUpRequestDto));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "정보 불러오기")
    @GetMapping("/user")
    public SingleResponse<UserMainResponseDto> getUserInfo() {
        return responseService.getSingleResponse(userService.findUserInfo());
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "정보 수정", description = "form-data 형식으로 전달 필요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "1006", description = "File Upload Fail", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping(value = "/user/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SingleResponse<String> updateUserInfo(@ModelAttribute UpdateUserRequestDto updateUserRequestDto) {
        return responseService.getSingleResponse(userService.updateUserInfo(updateUserRequestDto));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "비밀번호 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "1003", description = "Password Not Match", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping(value = "/user/changepw")
    public SingleResponse<String> changeUserPassword(@RequestBody PasswordDto passwordDto) {
        return responseService.getSingleResponse(userService.updatePassword(passwordDto));
    }

    @ExceptionHandler(ExistUserException.class)
    public CommonResponse existUserException(ExistUserException e) {
        return responseService.getErrorResponse(ErrorCode.EXIST_USER.getCode(), ErrorCode.EXIST_USER.getMessage());
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public CommonResponse notMatchPasswordException(NotMatchPasswordException e) {
        return responseService.getErrorResponse(ErrorCode.NOT_MATCH_PASSWORD.getCode(), ErrorCode.NOT_MATCH_PASSWORD.getMessage());
    }

    @ExceptionHandler(FileUploadFailException.class)
    public CommonResponse fileUploadFailException(FileUploadFailException e) {
        return responseService.getErrorResponse(ErrorCode.FILE_UPLOAD_FAIL.getCode(), ErrorCode.FILE_UPLOAD_FAIL.getMessage());
    }


}
