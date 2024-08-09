package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordResponseDto {

    private String newPassword;

    public UpdatePasswordResponseDto(String newPassword) {
        this.newPassword = newPassword;
    }
}
