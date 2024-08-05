package com.pj.planjourney.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdateUserResponseDto {

    private String nickname;


    public UpdateUserResponseDto(String nickname) {
        this.nickname = nickname;
    }
}
