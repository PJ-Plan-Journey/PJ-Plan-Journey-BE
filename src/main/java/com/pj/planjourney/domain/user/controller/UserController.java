package com.pj.planjourney.domain.user.controller;

import com.pj.planjourney.domain.refreshtoken.service.RefreshTokenService;
import com.pj.planjourney.domain.user.dto.*;
import com.pj.planjourney.domain.user.entity.User;
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
import org.springframework.security.core.Authentication;
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
    private final RefreshTokenService refreshTokenService;


    //회원가입
    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    //카카오 로그인


    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);
        if (refreshToken != null) {
            refreshTokenService.invalidateToken(refreshToken);
        }
        return ResponseEntity.ok("Logged out successfully");
    }



    //회원탈퇴
    @PostMapping("/signout")
    @PreAuthorize(("isAuthenticated()"))
    public ResponseEntity<?> signOut(@RequestBody SignOutRequestDto requestDto) {
        SignOutResponseDto responseDto = userService.signOut(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    //회원탈퇴 - 탈퇴
    @PostMapping("/{email}")
    public ResponseEntity<?> deactivateUser(@PathVariable String email) {
        userService.deactivateUser(email);
        return ResponseEntity.ok("삭제됨");
    }

    @GetMapping("")
    public  ResponseEntity<?> getUser(@RequestHeader("Authorization") String token){
        String cleanToken = token.replace("Bearer ", "");  // "Bearer " 제거
         userService.getUser(cleanToken);
        return   ResponseEntity.ok("철회됨") ;
    }

    //회원탈퇴 - 철회
    @PostMapping("/cancel-deactivation")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> cancelDeactivation(@RequestBody DeactivateUserRequestDto requestDto) {
        userService.cancelDeactivation(requestDto.getUser().getId());
        return ResponseEntity.ok("철회됨");
    }

    //회원정보 수정
    @PatchMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UpdateUserResponseDto> updateNickname(@RequestBody UpdateUserRequestDto requestDto) {
        UpdateUserResponseDto responseDto = userService.updateNickname(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    //비밀번호 변경


    //마이페이지


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 인증 시도
            Authentication authentication = jwtAuthenticationFilter.attemptAuthentication(request, response);

            if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                // 인증 후 JWT 토큰 생성 및 응답 헤더에 추가
                jwtAuthenticationFilter.successfulAuthentication(request, response, null, authentication);

                UserResponseDto userResponseDto = new UserResponseDto(
                        userDetails.getUser().getId(),
                        userDetails.getUsername(),
                        userDetails.getUser().getNickname()
                );

                return ResponseEntity.ok(new ApiResponse<>(userResponseDto, ApiResponseMessage.USER_LOGIN));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(null, ApiResponseMessage.ERROR));
            }
        } catch (Exception e) {
            log.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, ApiResponseMessage.ERROR));
        }
    }





}

