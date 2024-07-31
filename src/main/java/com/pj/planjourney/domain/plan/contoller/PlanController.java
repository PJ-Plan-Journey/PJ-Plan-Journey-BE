package com.pj.planjourney.domain.plan.contoller;

import com.pj.planjourney.domain.plan.dto.CreatePlanRequestDto;
import com.pj.planjourney.domain.plan.dto.CreatePlanResponseDto;
import com.pj.planjourney.domain.plan.service.PlanService;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ApiResponse<CreatePlanResponseDto> savePlan(@RequestHeader("USERID") Long userId,
                                                       @RequestBody CreatePlanRequestDto request) {
        CreatePlanResponseDto response = planService.save(userId, request);
        return new ApiResponse<>(response, ApiResponseMessage.PLAN_DELETED);
    }
}
