package com.pj.planjourney.domain.userPlan.repository;

import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
