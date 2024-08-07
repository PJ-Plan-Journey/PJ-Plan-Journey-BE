package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.plan.entity.Plan;
import lombok.Getter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
public class PlanCreateResponseDto {
    private Long id;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private User user;
    private City city;

    public PlanCreateResponseDto(Plan createPlan) {
        this.id = createPlan.getId();
        this.title = createPlan.getTitle();
        this.isPublished = createPlan.getIsPublished();
        this.publishedAt = LocalDateTime.now();
    }
}
