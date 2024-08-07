package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PlanCopyResponseDto {
    private Long planId;
    private String title;
    private String city;
    private Boolean isPublished;
    private LocalDateTime createdAt;
    private List<PlanDetail> planDetails;
    private Integer likeCount;
    private Long userId;

    public PlanCopyResponseDto(Plan newPlan) {
        this.planId = newPlan.getId();
        this.title = newPlan.getTitle();
        //this.city = newPlan.getCity();
        this.isPublished = newPlan.getIsPublished();
        this.createdAt = newPlan.getCreatedAt();
        this.planDetails = newPlan.getPlanDetails();
        this.likeCount = newPlan.getLikeCount();
   //     this.userId = newPlan.getUser().getId();
    }
}
