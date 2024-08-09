package com.pj.planjourney.domain.userPlan.dto;

import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class InvitedPlanResponseDto {

    private Long userPlanId;
    private Long planId;
    private String title;

    public InvitedPlanResponseDto(UserPlan userPlan) {
        this.userPlanId = userPlan.getId();
        this.planId = userPlan.getPlan().getId();
        this.title = userPlan.getPlan().getTitle();
    }
}
