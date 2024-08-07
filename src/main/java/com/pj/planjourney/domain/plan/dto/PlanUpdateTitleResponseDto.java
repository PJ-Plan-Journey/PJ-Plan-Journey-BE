package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanUpdateTitleResponseDto {
    private Long id;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private Long userId;
    private Long cityId;

    public PlanUpdateTitleResponseDto(Plan plan) {
        this.id = plan.getId();
        this.title = plan.getTitle();
        this.isPublished = plan.getIsPublished();
        this.publishedAt = plan.getPublishedAt();
        //this.userId = plan.getUser().getId();
        //this.cityId = plan.getCity().getId();

    }

}
