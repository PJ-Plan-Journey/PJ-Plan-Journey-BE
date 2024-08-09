package com.pj.planjourney.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Void> apiResponse = new ApiResponse<>(null, ApiResponseMessage.ERROR);

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
