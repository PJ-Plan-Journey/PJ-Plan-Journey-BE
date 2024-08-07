package com.pj.planjourney.domain.like.service;

import com.pj.planjourney.domain.like.dto.LikeCountResponseDto;
import com.pj.planjourney.domain.like.entity.Like;
import com.pj.planjourney.domain.like.repository.LikeRepository;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeCountResponseDto toggleLike(Long planId, Long userId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Like like = likeRepository.findByPlanAndUser(plan, user);
        if (like == null) {
            like = new Like(plan, user);
            likeRepository.save(like);
        } else {
            likeRepository.delete(like);
        }
        Long likeCount = likeRepository.countByPlan(plan);
        return new LikeCountResponseDto(planId, likeCount);
    }

}
