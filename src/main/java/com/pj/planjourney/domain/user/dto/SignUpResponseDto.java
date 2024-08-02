package com.pj.planjourney.domain.user.dto;


import com.pj.planjourney.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public SignUpResponseDto(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static SignUpResponseDto of(User user) {
        return new SignUpResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}





