package com.pj.planjourney.global.common.response;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonResponseUtil {
    public static <T> CommonResponse<T> success(T data, ApiResponseMessage message) {
        return new CommonResponse<>("success", message.getMessage(), data);
    }
}
