package com.pj.planjourney.domain.plandetail.entity;

import com.pj.planjourney.domain.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "plan_details")
public class PlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_detail_id")
    private Long id;

    private Integer sequence;

    private LocalDate date;

    private String placeName;

    private Double latitude;

    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public PlanDetail(Integer sequence, LocalDate date, String placeName, Double latitude, Double longitude, Plan plan) {
        this.sequence = sequence;
        this.date = date;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.plan = plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
