package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;

    public UserResponseDto(Long id, String username, String nickname) {
        this.id = id;
        this.email = username;
        this.nickname = nickname;
    }
}
