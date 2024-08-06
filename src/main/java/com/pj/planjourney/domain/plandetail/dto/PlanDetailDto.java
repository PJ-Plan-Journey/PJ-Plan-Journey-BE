package com.pj.planjourney.domain.plandetail.dto;

import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PlanDetailDto {

    private Integer sequence;
    private LocalDate date;
    private String placeName;
    private Double latitude;
    private Double longitude;

    public PlanDetailDto(PlanDetail planDetail) {
        this.sequence = planDetail.getSequence();
        this.date = planDetail.getDate();
        this.placeName = planDetail.getPlaceName();
        this.latitude = planDetail.getLatitude();
        this.longitude = planDetail.getLongitude();
    }
}
