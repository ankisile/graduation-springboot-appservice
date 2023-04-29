package com.oasis.springboot.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Exception {
    INVALIDATE_USER(1000, "해당하는 유저가 존재하지 않습니다."),
    EMPTY_AUTHENTICATION(1001, "Security Context에 인증 정보가 없습니다."),
    EXIST_USER(1002, "이미 가입된 유저입니다."),
    NOT_MATCH_PASSWORD(1003, "비밀번호가 일치하지 않습니다."),
    INVALIDATE_PLANT(1004, "해당하는 식물이 존재하지 않습니다."),
    INVALIDATE_GARDEN(1005, "해당하는 식물도감이 존재하지 않습니다."),
    FILE_UPLOAD_FAIL(1006, "파일 업로드에 실패했습니다.");

    private final int code;
    private final String message;
}
