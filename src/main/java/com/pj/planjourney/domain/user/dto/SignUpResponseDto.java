package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String authority;





    public SignUpResponseDto(Long id, String email, String nickname, String authority) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.authority = authority;
    }
}



