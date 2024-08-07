package com.pj.planjourney.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.user.dto.LoginRequestDto;
import com.pj.planjourney.domain.user.dto.DeactivateUserResponseDto;
import com.pj.planjourney.domain.user.dto.UserResponseDto;
import com.pj.planjourney.global.auth.repository.RefreshTokenRepository;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate( // authenticate인증처리 매서드
                    new UsernamePasswordAuthenticationToken( // 토큰 만들기
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String email = userDetails.getUsername();
        Long id = userDetails.getUser().getId();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        String accessToken = jwtUtil.createAccessToken(email, authorities);
        String refreshToken = jwtUtil.createRefreshToken(id);

        // 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("RefreshToken", refreshToken);

        // 사용자 정보를 포함한 JSON 객체 생성
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getNickname());
        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>(userResponseDto, ApiResponseMessage.USER_LOGIN);

        // 응답 본문에 사용자 정보 JSON 객체 추가
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(apiResponse);

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(responseJson);
    }


    @Override
    // 추가적인 메세지 관리
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}