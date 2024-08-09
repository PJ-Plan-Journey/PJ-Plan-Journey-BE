package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanUpdateTitleResponseDto {
    private Long id;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private String userNickName;
    private String cityName;

    public PlanUpdateTitleResponseDto(Plan plan, UserPlan userPlan) {
        this.id = plan.getId();
        this.title = plan.getTitle();
        this.isPublished = plan.getIsPublished();
        this.publishedAt = plan.getPublishedAt();
        this.userNickName = userPlan.getUser().getNickname();
        this.cityName = plan.getCity().getName();

    }

}
