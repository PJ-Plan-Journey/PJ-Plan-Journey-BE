package com.pj.planjourney.domain.plan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanCreateRequestDto {
    private String title;
    private Boolean isPublished;
    private Long userId;
    private Long cityId;

}
