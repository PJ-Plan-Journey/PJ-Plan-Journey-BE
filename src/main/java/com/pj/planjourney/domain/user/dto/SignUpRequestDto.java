package com.pj.planjourney.domain.user.dto;


import com.pj.planjourney.domain.user.entity.Role;
import com.pj.planjourney.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    private String email;
    private String password;
    private String nickname;
    private Role role = Role.USER;

    public SignUpRequestDto(String email, String password, String nickname,Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public static User signUp(SignUpRequestDto signUpRequestDto, PasswordEncoder passwordEncoder) {
        return new User(signUpRequestDto.getEmail(), passwordEncoder.encode(signUpRequestDto.getPassword()), signUpRequestDto.getNickname(),signUpRequestDto.getRole());
    }


}
