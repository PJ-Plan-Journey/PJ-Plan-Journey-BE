package com.pj.planjourney.global.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseMessage {
    USER_CREATED(HttpStatus.CREATED, "회원가입에 성공했습니다."),
    USER_RETRIEVED(HttpStatus.OK, "유저 조회에 성공했습니다."),
    USERS_RETRIEVED(HttpStatus.OK, "유저 목록 조회에 성공했습니다."),
    USER_DELETED(HttpStatus.OK, "회원 삭제에 성공했습니다."),
    REQUEST_SENT(HttpStatus.OK, "친구 요청이 전송되었습니다."),
    REQUEST_ACCEPTED(HttpStatus.OK, "친구 요청이 수락되었습니다."),
    REQUEST_REJECTED(HttpStatus.OK, "친구 요청이 거절되었습니다."),
    REQUEST_RETRIEVED(HttpStatus.OK, "친구 요청목록이 조회되었습니다."),
    RECEIVED_RETRIEVED(HttpStatus.OK, "받은 친구 요청목록이 조회되었습니다."),
    FRIENDS_RETRIEVED(HttpStatus.OK, "친구 목록이 조회되었습니다."),
    FRIEND_DELETED(HttpStatus.OK, "친구가 삭제되었습니다."),
    SUCCESS(HttpStatus.OK,"성공했습니다."),
    ERROR(HttpStatus.BAD_REQUEST,"잘못된 요청입니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ApiResponseMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

