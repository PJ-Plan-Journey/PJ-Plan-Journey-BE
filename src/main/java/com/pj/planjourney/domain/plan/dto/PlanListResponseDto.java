package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanListResponseDto {
    private Long id;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private Long userId;
    private Long cityId;

    public PlanListResponseDto(Plan plan) {
        this.id = plan.getId();;
        this.title = plan.getTitle();
        this.isPublished = plan.getIsPublished();
        this.publishedAt = plan.getPublishedAt();
//        this.userId = plan.getUser().getId();
//        this.cityId = plan.getCity().getId();
    }
}
