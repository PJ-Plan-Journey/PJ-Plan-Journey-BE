package com.pj.planjourney.global.common.exception;

import com.pj.planjourney.global.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<CommonResponse<Void>> handleException(BusinessLogicException e) {
        final CommonResponse<Void> response = new CommonResponse<>("error", e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getHttpStatus().value()));
    }
}
