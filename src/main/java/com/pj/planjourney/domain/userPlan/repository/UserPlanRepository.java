package com.pj.planjourney.domain.userPlan.repository;

import com.pj.planjourney.domain.userPlan.entity.InvitedStatus;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {

    List<UserPlan> findByUserIdAndInvitedStatus(Long userId, InvitedStatus invitedStatus);
}
