package com.pj.planjourney.domain.plan.repository;

import com.pj.planjourney.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}