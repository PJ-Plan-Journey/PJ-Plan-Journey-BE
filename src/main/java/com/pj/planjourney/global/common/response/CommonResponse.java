package com.pj.planjourney.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CommonResponse<T> {
    private String status;
    private String message;
    private T data;
}
