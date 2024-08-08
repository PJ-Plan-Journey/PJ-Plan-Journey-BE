package com.pj.planjourney.domain.user.dto;

import com.pj.planjourney.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DeactivateUserRequestDto {

    private long id;
    private User user;
    private LocalDateTime validAt;
    private LocalDateTime deletedAt;

    public DeactivateUserRequestDto(User user) {
        this.user = user;
    }
}
