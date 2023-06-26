package com.oasis.springboot.controller;

import com.oasis.springboot.common.exception.*;
import com.oasis.springboot.common.response.CommonResponse;
import com.oasis.springboot.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final ResponseService responseService;

    @ExceptionHandler(InvalidateUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonResponse invalidateUserException(InvalidateUserException e) {
        return responseService.getErrorResponse(ErrorCode.INVALIDATE_USER.getCode(), ErrorCode.INVALIDATE_USER.getMessage());
    }

    @ExceptionHandler(EmptyAuthenticationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonResponse emptyAuthenticationException(EmptyAuthenticationException e) {
        return responseService.getErrorResponse(ErrorCode.EMPTY_AUTHENTICATION.getCode(), ErrorCode.EMPTY_AUTHENTICATION.getMessage());
    }

    @ExceptionHandler(InvalidatePlantException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonResponse invalidatePlantException(InvalidatePlantException e) {
        return responseService.getErrorResponse(ErrorCode.INVALIDATE_PLANT.getCode(), ErrorCode.INVALIDATE_PLANT.getMessage());
    }

    @ExceptionHandler(InvalidateGardenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonResponse invalidateGardenException(InvalidateGardenException e) {
        return responseService.getErrorResponse(ErrorCode.INVALIDATE_GARDEN.getCode(), ErrorCode.INVALIDATE_GARDEN.getMessage());
    }
}
