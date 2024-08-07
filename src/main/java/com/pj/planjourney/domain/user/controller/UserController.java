package com.pj.planjourney.domain.user.controller;

import com.pj.planjourney.domain.user.dto.*;
import com.pj.planjourney.domain.user.service.UserService;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import com.pj.planjourney.global.auth.service.UserDetailsServiceImpl;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import com.pj.planjourney.global.jwt.filter.JwtAuthenticationFilter;
import com.pj.planjourney.global.jwt.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
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
    public ApiResponse<UserResponseDto> login(HttpServletRequest request, HttpServletResponse response) {
        // JwtAuthenticationFilter의 attemptAuthentication 메서드 호출
        jwtAuthenticationFilter.attemptAuthentication(request, response);

        // 사용자 정보를 포함한 응답 반환
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getNickname());
        return new ApiResponse<>(null, ApiResponseMessage.USER_LOGIN);
    }




}

