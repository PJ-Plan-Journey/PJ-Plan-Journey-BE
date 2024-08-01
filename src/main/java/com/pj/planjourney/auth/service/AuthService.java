package com.pj.planjourney.auth.service;

import com.pj.planjourney.auth.dto.JwtTokenDto;
import com.pj.planjourney.auth.jwt.JwtTokenProvider;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UsserResponseDto signup(UserRequestDto userRequestDto){
        if(userRepository.existByEmail(userRequestDTO.getEmail())){
            throw new IllegalArgumentException("공통 포맷");
        }

        User user = userRequestDto.toMember(passwordEncoder);
        return UsserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public JwtTokenDto login(UserRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDTO(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtTokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtTokenDto;
    }

    //accessToken 재발급
    @Transactional
    public JwtTokenDto reissue(TokenRequestDTO tokenRequestDTO) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDTO.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDTO.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDTO(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return jwtTokenDto;
    }
}
