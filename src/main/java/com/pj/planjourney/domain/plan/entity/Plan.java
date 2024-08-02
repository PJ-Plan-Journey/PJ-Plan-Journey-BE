package com.pj.planjourney.domain.plan.entity;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.like.entity.Like;
import com.pj.planjourney.domain.plan.dto.CreatePlanRequestDto;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "plans")
@NoArgsConstructor
public class Plan extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    private String title;

    private Boolean isPublished;

    private LocalDateTime publishedAt;

    @OneToMany(mappedBy = "plan")
    private List<UserPlan> userPlans = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "plan")
    private List<PlanDetail> planDetails = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Like> likes = new ArrayList<>();

    public Plan(CreatePlanRequestDto request, City city) {
        this.title = request.getTitle();
        this.isPublished = false;
        this.city = city;
    }

    public void addPlanDetail(PlanDetail planDetail) {
        planDetails.add(planDetail);
        planDetail.setPlan(this);
    }
}
