package com.pj.planjourney.domain.like.repository;

import com.pj.planjourney.domain.like.entity.Like;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByPlanAndUser(Plan plan, User user);

    Long countByPlan(Plan plan);
}
