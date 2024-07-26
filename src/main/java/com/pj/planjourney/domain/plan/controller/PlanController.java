package com.pj.planjourney.domain.plan.controller;

import com.pj.planjourney.domain.plan.dto.PlanCreateRequestDto;
import com.pj.planjourney.domain.plan.dto.PlanCreateResponseDto;
import com.pj.planjourney.domain.plan.dto.PlanUpdateTitleRequestDto;
import com.pj.planjourney.domain.plan.dto.PlanUpdateTitleResponseDto;
import com.pj.planjourney.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanCreateResponseDto> createPlan(@RequestBody PlanCreateRequestDto requestDto) {
        PlanCreateResponseDto responseDto = planService.createPlan(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{plansId}")
    public ResponseEntity<PlanUpdateTitleResponseDto> updatePlanTitle(@PathVariable Long plansId, @RequestBody PlanUpdateTitleRequestDto requestDto) {
        PlanUpdateTitleResponseDto responseDto = planService.updatePlanTitle(plansId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
