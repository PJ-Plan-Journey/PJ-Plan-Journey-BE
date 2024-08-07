package com.pj.planjourney.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeCountResponseDto {
    private Long planId;
    private Long likeCount;

    public LikeCountResponseDto(Long planId, Long likeCount) {
        this.planId = planId;
        this.likeCount = likeCount;
    }
}
