package com.pj.planjourney.domain.plandetail.dto;

import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreatePlanDetailResponseDto {
    private Long planDetailId;
    private Integer sequence;
    private LocalDate date;
    private String placeName;
    private Double latitude;
    private Double longitude;

    public CreatePlanDetailResponseDto(PlanDetail planDetail) {
        this.planDetailId = planDetail.getId();
        this.sequence = planDetail.getSequence();
        this.date = planDetail.getDate();
        this.placeName = planDetail.getPlaceName();
        this.latitude = planDetail.getLatitude();
        this.longitude = planDetail.getLongitude();
    }
}
