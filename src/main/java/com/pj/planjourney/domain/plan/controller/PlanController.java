package com.pj.planjourney.domain.plan.controller;

import com.pj.planjourney.domain.plan.dto.*;
import com.pj.planjourney.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;

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

    @PatchMapping("/{plansId}")
    public ResponseEntity<PlanUpdateTitleResponseDto> updatePlanTitle(@PathVariable Long plansId, @RequestBody PlanUpdateTitleRequestDto requestDto) {
        PlanUpdateTitleResponseDto responseDto = planService.updatePlanTitle(plansId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/{plansId}")
    public ResponseEntity<PlanInfoResponseDto> getPlan(@PathVariable Long plansId) {
        PlanInfoResponseDto responseDto = planService.getPlan(plansId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PlanListResponseDto>> getAllPlans() {
        List<PlanListResponseDto> responseDto = planService.getAllPlans();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{plansId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long plansId) {
        planService.deletePlan(plansId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
