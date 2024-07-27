package com.pj.planjourney.global.common.exception;

import com.pj.planjourney.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(BusinessLogicException e) {
        final ApiResponse<ErrorResponse> response = new ApiResponse<>("error", e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getHttpStatus().value()));
    }
}
