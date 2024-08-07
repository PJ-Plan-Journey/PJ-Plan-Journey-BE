package com.pj.planjourney.domain.plandetail.controller;

import com.pj.planjourney.domain.plandetail.dto.EditPlanDetailRequestDto;
import com.pj.planjourney.domain.plandetail.dto.EditPlanDetailResponseDto;
import com.pj.planjourney.domain.plandetail.service.EditPlanDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class EditPlanDetailController {

    private final EditPlanDetailService editPlanService;

    @MessageMapping("/room/{roomId}/entered")
    @SendTo("/sub/room/{roomId}")
    public String entered(@DestinationVariable(value = "roomId") String roomId) {
        return "초대된 친구가 입장하였습니다.";
    }

    @MessageMapping("/edit/room/{roomId}")
    @SendTo("/sub/room/{roomId}")
    public EditPlanDetailResponseDto editPlan(@DestinationVariable(value = "roomId") String roomId, EditPlanDetailRequestDto request) {
        return editPlanService.editPlan(request);

    }
}