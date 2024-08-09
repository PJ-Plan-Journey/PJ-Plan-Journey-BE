package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String nickname;

    public UserResponseDto(String username, String nickname) {
        this.email = username;
        this.nickname = nickname;
    }
}
