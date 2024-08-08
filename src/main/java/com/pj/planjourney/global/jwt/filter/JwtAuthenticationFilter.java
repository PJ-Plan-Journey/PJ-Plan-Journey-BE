package com.pj.planjourney.global.jwt.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.user.dto.LoginRequestDto;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            System.out.println("Request body: " + body);

            if (body.isEmpty()) {
                throw new RuntimeException("Request body is empty.");
            }

            LoginRequestDto requestDto = new ObjectMapper().readValue(body, LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authRequest
                    = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

            setDetails(request, authRequest);
            return getAuthenticationManager().authenticate( // authenticate인증처리 매서드
                    authRequest
            );
        } catch (IOException e) {
            log.info("Error reading request body: " + e.getMessage());
            throw new RuntimeException("Failed to read request body", e);
        } catch (IllegalArgumentException e) {
            log.error("Invalid request data: " + e.getMessage());
            throw new RuntimeException("Invalid request data", e);
        }
    }




    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        // Call success handler
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String email = userDetails.getUsername();
        Long id = userDetails.getUser().getId();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        String accessToken = jwtUtil.createAccessToken(email, authorities);
        String refreshToken = jwtUtil.createRefreshToken(id);
        // Send tokens in response headers
        response.setHeader("Authorization", accessToken);
        response.setHeader("RefreshToken", refreshToken);
    }
//
//
//
//
//    @Override
//    // 추가적인 메세지 관리
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        log.info("로그인 실패");
//        response.setStatus(401);
//    }
}