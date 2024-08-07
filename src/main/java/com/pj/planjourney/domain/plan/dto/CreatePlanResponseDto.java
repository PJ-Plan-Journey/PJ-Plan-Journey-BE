package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plandetail.dto.CreatePlanDetailResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePlanResponseDto {
    private Long planId;
    private String title;
    private String city;
    private LocalDateTime createdAt;
    private List<CreatePlanDetailResponseDto> planDetails = new ArrayList<>();

    public CreatePlanResponseDto(Plan plan) {
        planId = plan.getId();
        title = plan.getTitle();
        city = plan.getCity().getName();
        createdAt = plan.getCreatedAt();
        planDetails = plan.getPlanDetails().stream().map(CreatePlanDetailResponseDto::new).toList();
    }
}
