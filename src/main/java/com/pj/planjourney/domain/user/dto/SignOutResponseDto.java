package com.pj.planjourney.domain.user.dto;

import com.pj.planjourney.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SignOutResponseDto {

    private Long id;
    private Long userId;
    private LocalDateTime deletedAt;
    private LocalDateTime validAt;

    public SignOutResponseDto(Long id, Long userId, LocalDateTime deletedAt, LocalDateTime validAt) {
        this.id = id;
        this.userId = userId;
        this.deletedAt = deletedAt;
        this.validAt = validAt;
    }
}
