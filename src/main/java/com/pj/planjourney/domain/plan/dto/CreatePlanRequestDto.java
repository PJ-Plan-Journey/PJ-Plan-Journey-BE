package com.pj.planjourney.domain.plan.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plandetail.dto.CreatePlanDetailRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePlanRequestDto {

    private String title;
    private String city;
    private List<CreatePlanDetailRequestDto> planDetails = new ArrayList<>();

    public CreatePlanRequestDto(Plan plan) {
        this.title = plan.getTitle();
        this.city = plan.getCity().getName();
        planDetails = plan.getPlanDetails().stream().map(CreatePlanDetailRequestDto::new).toList();
    }
}
