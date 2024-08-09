package com.pj.planjourney.domain.like.controller;

import com.pj.planjourney.domain.like.dto.LikeCountResponseDto;
import com.pj.planjourney.domain.like.service.LikeService;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plans/{planId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PatchMapping
    public ApiResponse<LikeCountResponseDto> toggleLike(@PathVariable Long planId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        LikeCountResponseDto responseDto = likeService.toggleLike(planId, userId);
        return new ApiResponse<>(responseDto, ApiResponseMessage.SUCCESS);
    }
}
