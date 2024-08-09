package com.pj.planjourney.domain.userPlan.dto;

import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AcceptInvitePlanRequestDto {

    private Long planId;

    public AcceptInvitePlanRequestDto(UserPlan userPlan) {
        this.planId = userPlan.getPlan().getId();
    }
}
