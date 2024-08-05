package com.pj.planjourney.domain.user.controller;

import com.pj.planjourney.domain.user.dto.*;
import com.pj.planjourney.domain.user.service.UserService;
import com.pj.planjourney.global.auth.service.UserDetailsServiceImpl;
import com.pj.planjourney.global.jwt.filter.JwtAuthenticationFilter;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    //회원가입
    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    //카카오 로그인


    //로그아웃


    //회원탈퇴
    //회원정보 수정
    @PatchMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UpdateUserResponseDto> updateNickname(@RequestBody UpdateUserRequestDto requestDto) {
        UpdateUserResponseDto responseDto = userService.updateNickname(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    //비밀번호 변경?해야겟죠


    //마이페이지


    //로그인
    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // JwtAuthenticationFilter의 attemptAuthentication 메서드 호출
        jwtAuthenticationFilter.attemptAuthentication(request, response);

        // JWT 발급 후 성공적인 인증 처리
        try {
            jwtAuthenticationFilter.successfulAuthentication(request, response, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}

