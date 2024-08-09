package com.pj.planjourney.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원를 찾을 수 없습니다."),
    USER_NOT_LOGIN(HttpStatus.NOT_ACCEPTABLE,"로그인에 실패하였습니다"),
    USER_NOT_LOGOUT(HttpStatus.NOT_ACCEPTABLE,"로그아웃에 실패하였습니다"),
    REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "이미 보낸 친구 요청입니다."),
    REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "친구요청을 찾을 수 없습니다."),
    CITY_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 도시를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
