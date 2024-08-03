package com.pj.planjourney.domain.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 10)
@AllArgsConstructor
@Getter
@ToString
public class Token {
    @Id
    private Long id;
    private String refreshToken;

    public Token(String token, Long userId) {
        this.refreshToken = token;
        this.id = userId;
    }
}
