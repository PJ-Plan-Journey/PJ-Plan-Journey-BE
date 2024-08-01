package com.pj.planjourney.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.auth.dto.JwtTokenDto;
import com.pj.planjourney.auth.jwt.JwtTokenProvider;
import com.pj.planjourney.auth.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtLoginFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청에서 사용자 자격 증명 추출
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String email = credentials.get("email");
            String password = credentials.get("password");

            // 사용자 자격 증명으로 Authentication 객체 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 로그인 성공 시, JWT 생성
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDTO(authResult);

        // JWT를 응답 헤더에 추가
        response.addHeader(JwtTokenProvider.AUTHORIZATION_HEADER, JwtTokenProvider.BEARER_PREFIX + jwtTokenDto.getAccessToken());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), jwtTokenDto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 로그인 실패 시, 적절한 오류 응답 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid username or password\"}");
    }
}