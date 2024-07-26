package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfoResponseDto {
    private Long id;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private Long userId;
    private Long cityId;

    public PlanInfoResponseDto(Plan plan) {
        this.id = plan.getId();
        this.title = plan.getTitle();
        this.isPublished = plan.getIsPublished();
        this.publishedAt = plan.getPublishedAt();
        this.userId = plan.getUser() != null ? plan.getUser().getId() : null; // User가 null일 수도 있음
        this.cityId = plan.getCity() != null ? plan.getCity().getId() : null; // City가 null일 수도 있음
    }
}
