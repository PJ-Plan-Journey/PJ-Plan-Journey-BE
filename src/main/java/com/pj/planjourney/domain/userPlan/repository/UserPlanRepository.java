package com.pj.planjourney.domain.userPlan.repository;

import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {

    Optional<UserPlan> findByUserAndPlan(User user, Plan plan);
}
