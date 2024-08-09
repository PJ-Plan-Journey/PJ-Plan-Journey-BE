package com.pj.planjourney.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.refreshtoken.service.RefreshTokenService;
import com.pj.planjourney.global.auth.service.UserDetailsServiceImpl;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 로그아웃 요청 처리
            if (isLogoutRequest(req)) {
                handleLogout(req, res);
                return; // 로그아웃 처리 후 필터 체인의 나머지 부분을 실행하지 않도록 합니다.
            }

            String accessToken = jwtUtil.getAccessTokenFromHeader(req);
            String refreshToken = jwtUtil.getRefreshTokenFromHeader(req);

            if (StringUtils.hasText(accessToken)) {
                String tokenValidationResult = jwtUtil.validateToken(accessToken);
                if (tokenValidationResult != null) {
                    // 토큰 검증 실패 시
                    log.error("Token Error: " + tokenValidationResult);
                    if (!StringUtils.hasText(refreshToken)) {
                        log.error("Refresh Token is missing");
                        throw new AuthenticationCredentialsNotFoundException("Refresh Token is missing");
                    }
                } else {
                    // 토큰이 유효한 경우
                    Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                    try {
                        setAuthentication(info.getSubject()); // 사용자 인증 설정
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new AuthenticationServiceException("Authentication failed", e);
                    }
                }
            }

            filterChain.doFilter(req, res);

        } catch (AuthenticationCredentialsNotFoundException | AuthenticationServiceException e) {
            handleExceptionInFilter(e, res);
        }
    }

    private void handleExceptionInFilter(Exception e, HttpServletResponse res) throws IOException {
        // 예외를 처리하여 응답을 직접 작성
        ApiResponse<Void> apiResponse = new ApiResponse<>(null, ApiResponseMessage.ERROR);
        ResponseEntity<ApiResponse<Void>> responseEntity = ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(apiResponse);

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod()) && "/users/logout".equalsIgnoreCase(request.getRequestURI());
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtil.getAccessTokenFromHeader(request);
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);
        try {
            if (accessToken != null) {
                refreshTokenService.invalidateToken(accessToken);
            }
            // 리프레시 토큰 무효화 및 삭제
            if (refreshToken != null) {
                refreshTokenService.invalidateToken(refreshToken);
                Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
                if (claims != null) {
                    String userIdString = claims.getSubject();
                    if (userIdString != null) {
                        Long userId = Long.valueOf(userIdString);
                        refreshTokenService.deleteRefreshToken(userId);
                        log.info("Deleted refresh token for user ID: " + userId);
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            log.error("Logout handling failed: " + e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

        // 인증 처리
        public void setAuthentication (String email){
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = createAuthentication(email); //구현체 반환
            context.setAuthentication(authentication); // 다시담기

            SecurityContextHolder.setContext(context);
        }

        // 인증 객체 생성
        private Authentication createAuthentication (String username){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

    }