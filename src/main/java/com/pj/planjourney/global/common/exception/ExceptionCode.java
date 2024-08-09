package com.pj.planjourney.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원를 찾을 수 없습니다."),
    REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "이미 보낸 친구 요청입니다."),
    REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "친구요청을 찾을 수 없습니다."),
    CITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 도시를 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 알림을 찾을 수 없습니다."),
    SENDER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청자를 찾을 수 없습니다."),
    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저는 친구가 아닙니다."),
    FRIEND_NOT_INVITED(HttpStatus.NOT_FOUND, "초대할 친구를 선택하세요."),
    RESPONSE_ALREADY(HttpStatus.NOT_FOUND, "이미 응답이 처리되었습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
