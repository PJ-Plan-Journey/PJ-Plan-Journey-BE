package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;



    public SignUpResponseDto(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }


}



