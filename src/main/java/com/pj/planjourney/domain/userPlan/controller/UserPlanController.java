package com.pj.planjourney.domain.userPlan.controller;

import com.pj.planjourney.domain.userPlan.dto.AcceptInvitePlanRequestDto;
import com.pj.planjourney.domain.userPlan.dto.InviteFriendsRequestDto;
import com.pj.planjourney.domain.userPlan.dto.InvitedPlanResponseDto;
import com.pj.planjourney.domain.userPlan.service.UserPlanService;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invites")
public class UserPlanController {

    private final UserPlanService userPlanService;

    @PostMapping("/{planId}")
    public ApiResponse<Void> inviteFriendsToPlan(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long planId,
                                                 @RequestBody InviteFriendsRequestDto request) {
        userPlanService.invite(userDetails.getUser(), planId, request);
        return new ApiResponse<>(null, ApiResponseMessage.REQUEST_SENT);
    }

    @GetMapping
    public ApiResponse<List<InvitedPlanResponseDto>> getInvitedPlans(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<InvitedPlanResponseDto> response = userPlanService.getInvitedPlans(userDetails.getUser().getId());
        return new ApiResponse<>(response, ApiResponseMessage.SUCCESS);
    }

    @PostMapping("/{userPlanId}/accept")
    public ApiResponse<AcceptInvitePlanRequestDto> acceptInvitePlan(@PathVariable Long userPlanId) {
        AcceptInvitePlanRequestDto response  = userPlanService.acceptInvitePlan(userPlanId);
        return new ApiResponse<>(response, ApiResponseMessage.SUCCESS);
    }

    @PostMapping("/{userPlanId}/reject")
    public ApiResponse<Void> rejectInvitePlan(@PathVariable Long userPlanId) {
        userPlanService.rejectInvitePlan(userPlanId);
        return new ApiResponse<>(null, ApiResponseMessage.SUCCESS);
    }
}
