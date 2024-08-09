package com.pj.planjourney.domain.plan.service;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.comment.repository.CommentRepository;
import com.pj.planjourney.domain.plan.dto.*;
import com.pj.planjourney.domain.city.repository.CityRepository;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import com.pj.planjourney.domain.plandetail.dto.CreatePlanDetailRequestDto;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import com.pj.planjourney.domain.plandetail.repository.PlanDetailRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import java.util.List;
import com.pj.planjourney.domain.userPlan.entity.InvitedStatus;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import com.pj.planjourney.domain.userPlan.repository.UserPlanRepository;
import com.pj.planjourney.global.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pj.planjourney.global.common.exception.ExceptionCode.CITY_NOT_FOUND;
import static com.pj.planjourney.global.common.exception.ExceptionCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final PlanDetailRepository planDetailRepository;
    private final UserPlanRepository userPlanRepository;
  
    public CreatePlanResponseDto save(Long userId, CreatePlanRequestDto request) {
        User user = findUserById(userId);
        City city = findCityByName(request.getCity());

        Plan plan = planRepository.save(new Plan(request, city));
        for (CreatePlanDetailRequestDto detailDto : request.getPlanDetails()) {
            PlanDetail planDetail = detailDto.toEntity(plan);
            plan.addPlanDetail(planDetail);
            planDetailRepository.save(planDetail);
        }
        userPlanRepository.save(new UserPlan(user, plan, InvitedStatus.ACCEPTED));
        return new CreatePlanResponseDto(plan);
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
  
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(USER_NOT_FOUND));
    }
  

    private City findCityByName(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new BusinessLogicException(CITY_NOT_FOUND));
    }
}
