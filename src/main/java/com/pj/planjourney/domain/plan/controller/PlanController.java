package com.pj.planjourney.domain.plan.controller;

import com.pj.planjourney.domain.plan.dto.*;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanCreateResponseDto> createPlan(@RequestHeader Long userId, @RequestBody PlanCreateRequestDto requestDto) {
        PlanCreateResponseDto responseDto = planService.createPlan(requestDto, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<PlanUpdateTitleResponseDto> updatePlanTitle(@PathVariable Long planId, @RequestBody PlanUpdateTitleRequestDto requestDto) {
        PlanUpdateTitleResponseDto responseDto = planService.updatePlanTitle(planId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/{planId}")
    public ResponseEntity<PlanInfoResponseDto> getPlan(@PathVariable Long planId) {
        PlanInfoResponseDto responseDto = planService.getPlan(planId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PlanListResponseDto>> getAllPlans() {
        List<PlanListResponseDto> responseDto = planService.getAllPlans();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        planService.deletePlan(planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{planId}/publish")
    public ResponseEntity<Void> publishPlan(@PathVariable Long planId, @RequestHeader Long userId) {
        planService.publishPlan(planId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{planId}")
    public ResponseEntity<PlanCopyResponseDto> copyPlan(@PathVariable Long planId, @RequestHeader Long userId) {
        PlanCopyResponseDto responseDto = planService.copyPlan(planId, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
