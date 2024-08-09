package com.pj.planjourney.domain.userPlan.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InviteFriendsRequestDto {

    private final List<Long> inviteFriends = new ArrayList<>();
}
