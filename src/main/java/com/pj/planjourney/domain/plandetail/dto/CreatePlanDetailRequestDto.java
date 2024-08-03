package com.pj.planjourney.domain.plandetail.dto;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreatePlanDetailRequestDto {

    private Integer sequence;
    private LocalDate date;
    private String placeName;
    private Double latitude;
    private Double longitude;

    public PlanDetail toEntity(Plan plan) {
        return new PlanDetail(
                sequence,
                date,
                placeName,
                latitude,
                longitude,
                plan);
    }

    public CreatePlanDetailRequestDto(PlanDetail planDetail) {
        this.sequence = planDetail.getSequence();
        this.date = planDetail.getDate();
        this.placeName = planDetail.getPlaceName();
        this.latitude = planDetail.getLatitude();
        this.longitude = planDetail.getLongitude();
    }
}
