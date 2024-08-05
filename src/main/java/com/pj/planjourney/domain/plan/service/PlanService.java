package com.pj.planjourney.domain.plan.service;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.city.repository.CityRepository;
import com.pj.planjourney.domain.plan.dto.CreatePlanRequestDto;
import com.pj.planjourney.domain.plan.dto.CreatePlanResponseDto;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import com.pj.planjourney.domain.plandetail.dto.CreatePlanDetailRequestDto;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import com.pj.planjourney.domain.plandetail.repository.PlanDetailRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
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

        userPlanRepository.save(new UserPlan(user, plan, InvitedStatus.ACCEPT));
        return new CreatePlanResponseDto(plan);
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
