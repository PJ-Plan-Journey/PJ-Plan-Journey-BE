package com.pj.planjourney.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원를 찾을 수 없습니다."),
    REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "Friend request already sent"),
    REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "Friend request not found");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
