package com.pj.planjourney.domain.plan.service;

import com.pj.planjourney.domain.plan.dto.*;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public PlanCreateResponseDto createPlan(PlanCreateRequestDto planCreateRequestDto) {
        Plan plan = new Plan(planCreateRequestDto);
        Plan createPlan = planRepository.save(plan);
        PlanCreateResponseDto planCreateResponseDto = new PlanCreateResponseDto(createPlan);
        return planCreateResponseDto;
    }

    public PlanUpdateTitleResponseDto updatePlanTitle(Long planId, PlanUpdateTitleRequestDto requestDto) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        plan.updatePlan(requestDto);
        Plan updatedPlan = planRepository.save(plan);
        return new PlanUpdateTitleResponseDto(updatedPlan);
    }

    public PlanInfoResponseDto getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        return new PlanInfoResponseDto(plan);
    }

    public List<PlanListResponseDto> getAllPlans() {
        return planRepository.findAll().stream()
                .map(PlanListResponseDto::new).toList();
    }

    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }
}
