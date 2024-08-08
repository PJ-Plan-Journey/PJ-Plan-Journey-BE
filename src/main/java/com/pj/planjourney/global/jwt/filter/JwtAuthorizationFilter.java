package com.pj.planjourney.global.jwt.filter;

import com.pj.planjourney.domain.refreshtoken.service.RefreshTokenService;
import com.pj.planjourney.global.auth.service.UserDetailsServiceImpl;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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
                    log.error("Refresh Token Error");
                    return;
                }
            } else {
                // 토큰이 유효한 경우
                Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                try {
                    setAuthentication(info.getSubject()); // 사용자 인증 설정
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(req, res);
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod()) && "/users/logout".equalsIgnoreCase(request.getRequestURI());
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtil.getAccessTokenFromHeader(request);
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

        if (accessToken != null) {
            refreshTokenService.invalidateToken(accessToken);
        }
        if (refreshToken != null) {
            refreshTokenService.invalidateToken(refreshToken);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email); //구현체 반환
        context.setAuthentication(authentication); // 다시담기

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}