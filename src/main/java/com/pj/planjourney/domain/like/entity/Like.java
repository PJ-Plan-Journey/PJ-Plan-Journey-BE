package com.pj.planjourney.domain.like.entity;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Like(Plan plan, User user) {
        this.plan = plan;
        this.user = user;
    }

}
