package com.pj.planjourney.global.common.response;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiResponseUtil {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", message, null);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>("error", message, null);
    }
}
