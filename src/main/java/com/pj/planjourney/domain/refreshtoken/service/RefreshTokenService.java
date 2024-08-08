package com.pj.planjourney.domain.refreshtoken.service;

import com.pj.planjourney.domain.refreshtoken.Token;
import com.pj.planjourney.domain.refreshtoken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(Long userId, String refreshToken) {
        try {
            Token tokenEntity = new Token(refreshToken, userId);
            refreshTokenRepository.save(tokenEntity);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set("refreshToken:" + userId, refreshToken);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save refresh token: " + e.getMessage());
        }
    }



    public String getRefreshToken(Long userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("refreshToken:" + userId);
    }

    public void invalidateToken(String token) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "blacklist:" + token;
        valueOperations.set(key, token, 24, TimeUnit.HOURS); // 24시간 후 만료

    }

    public boolean isTokenBlacklisted(String token) {
        ValueOperations<String, String > valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("blacklist:" + token) != null;
    }

    public void deleteRefreshToken(Long userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.getOperations().delete("refreshToken:" + userId);
    }
}
