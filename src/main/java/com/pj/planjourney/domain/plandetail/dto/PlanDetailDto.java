package com.pj.planjourney.domain.plandetail.dto;

import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlanDetailDto {

    private final Integer sequence;
    private final LocalDate date;
    private final String placeName;
    private final Double latitude;
    private final Double longitude;

    public PlanDetailDto(PlanDetail planDetail) {
        this.sequence = planDetail.getSequence();
        this.date = planDetail.getDate();
        this.placeName = planDetail.getPlaceName();
        this.latitude = planDetail.getLatitude();
        this.longitude = planDetail.getLongitude();
    }
}
