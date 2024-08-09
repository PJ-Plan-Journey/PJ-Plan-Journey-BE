package com.pj.planjourney.domain.userPlan.entity;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_plans")
public class UserPlan {

    @Id
    @Column(name = "user_plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Enumerated(EnumType.STRING)
    private InvitedStatus invitedStatus;

    public UserPlan(User user, Plan plan, InvitedStatus invitedStatus) {
        this.user = user;
        this.plan = plan;
        this.invitedStatus = invitedStatus;
    }

    public UserPlan(User user, Plan plan) {
        this.user = user;
        this.plan = plan;
    }
}
