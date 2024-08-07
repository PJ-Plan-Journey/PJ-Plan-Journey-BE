package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanListResponseDto {
    private Long planId;
    private String title;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String nickName;
    private Integer likeCount;

    public PlanListResponseDto(Plan plan) {
        this.planId = plan.getId();
        this.title = plan.getTitle();
        this.isPublished = plan.getIsPublished();
        this.publishedAt = plan.getPublishedAt();
    //    this.nickName = plan.getUser().getNickname();
        this.createdAt = plan.getCreatedAt();
        this.likeCount = plan.getLikeCount();
//        this.cityId = plan.getCity().getId();
    }
}
