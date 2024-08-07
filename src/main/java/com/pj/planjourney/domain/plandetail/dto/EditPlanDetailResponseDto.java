package com.pj.planjourney.domain.plandetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class EditPlanDetailResponseDto {

    private Long planId;
    private final Map<LocalDate, List<PlanDetailDto>> groupedByDate = new HashMap<>();

    public EditPlanDetailResponseDto(List<PlanDetailDto> planDetails, Long planId) {
        this.planId = planId;
        for (PlanDetailDto detail : planDetails) {
            groupedByDate.computeIfAbsent(detail.getDate(), k -> new ArrayList<>()).add(detail);
        }
    }
}
