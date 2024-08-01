package com.pj.planjourney.domain.plandetail.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class EditPlanDetailResponseDto {

    private final Map<LocalDate, List<PlanDetailDto>> groupedByDate = new HashMap<>();

    public EditPlanDetailResponseDto(List<PlanDetailDto> planDetails) {
        for (PlanDetailDto detail : planDetails) {
            groupedByDate.computeIfAbsent(detail.getDate(), k -> new ArrayList<>()).add(detail);
        }
    }
}
