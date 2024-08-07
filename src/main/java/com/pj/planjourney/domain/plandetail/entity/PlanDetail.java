package com.pj.planjourney.domain.plandetail.entity;

import com.pj.planjourney.domain.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "plan_details")
@NoArgsConstructor
public class PlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_detail_id")
    private Long id;

    private Integer sequence;

    private LocalDate date;

    private String name;

    private Double latitude;

    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public PlanDetail(PlanDetail original, Plan newPlan) {
        this.plan = newPlan;
        this.sequence = original.getSequence();
        this.date = original.getDate();
        this.name = original.getName();
        this.latitude = original.getLatitude();
        this.longitude = original.getLongitude();
    }
}
