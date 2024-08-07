package com.pj.planjourney.domain.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanListRequestDto {
    // 필터링, 페이징, 정렬 등에 필요한 필드 추가 가능
    private Long userId;
    private Long cityId;
}
