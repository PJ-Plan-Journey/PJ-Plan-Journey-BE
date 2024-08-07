package com.pj.planjourney.domain.like.controller;

import com.pj.planjourney.domain.like.dto.LikeCountResponseDto;
import com.pj.planjourney.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plans/{planId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PatchMapping
    public ResponseEntity<LikeCountResponseDto> toggleLike(@PathVariable Long planId, @RequestHeader Long userId) {
        LikeCountResponseDto responseDto = likeService.toggleLike(planId, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
