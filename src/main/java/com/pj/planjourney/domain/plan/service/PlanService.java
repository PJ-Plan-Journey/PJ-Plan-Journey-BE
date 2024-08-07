package com.pj.planjourney.domain.plan.service;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.comment.repository.CommentRepository;
import com.pj.planjourney.domain.plan.dto.*;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import com.pj.planjourney.domain.plandetail.repository.PlanDetailRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final PlanDetailRepository planDetailRepository;
    private final CommentRepository commentRepository;

    public PlanCreateResponseDto createPlan(PlanCreateRequestDto planCreateRequestDto,Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Plan plan = planCreateRequestDto.toEntity(user);
        Plan createPlan = planRepository.save(plan);
        PlanCreateResponseDto planCreateResponseDto = new PlanCreateResponseDto(createPlan);
        return planCreateResponseDto;
    }

    public PlanUpdateTitleResponseDto updatePlanTitle(Long planId, PlanUpdateTitleRequestDto requestDto) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        plan.updatePlan(requestDto);
        Plan updatedPlan = planRepository.save(plan);
        return new PlanUpdateTitleResponseDto(updatedPlan);
    }
    // 상세 게시글 확인
    public PlanInfoResponseDto getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        List<PlanDetail> planDetails = planDetailRepository.findByPlan(plan);
        return new PlanInfoResponseDto(plan, plan.getCity(), planDetails);
    }

    // isPublished가 true인 게시글 확인
    public List<PlanListResponseDto> getAllPlans() {
        return planRepository.findByIsPublishedTrue().stream()
                .map(PlanListResponseDto::new).toList();
    }

    // 게시글 삭제 기능
    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }

    // 내 일정 publish 하기
    public void publishPlan(Long planId, Long userId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        plan.publish(true);
        planRepository.save(plan);
    }
    // 다른 사람 일정 복사해서 가져오기
    public PlanCopyResponseDto copyPlan(Long planId, Long userId) {
        Plan originalPlan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Plan newPlan = new Plan(originalPlan, user);

        return new PlanCopyResponseDto(planRepository.save(newPlan));
    }
}
