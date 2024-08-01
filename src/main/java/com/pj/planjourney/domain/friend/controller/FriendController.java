package com.pj.planjourney.domain.friend.controller;

import com.pj.planjourney.domain.friend.dto.*;
import com.pj.planjourney.domain.friend.service.FriendService;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request")
    public ApiResponse<Void> sendFriendRequest(@RequestBody FriendRequestSendDto requestCreateDto, @RequestHeader Long userId) {
        friendService.sendFriendRequest(requestCreateDto,userId);
        return new ApiResponse<>(null, ApiResponseMessage.REQUEST_SENT);
    }

    @GetMapping("/sentLists")
    public ApiResponse<List<FriendRequestResponseDto>> getSentFriendRequests(@RequestHeader Long userId) {
        List<FriendRequestResponseDto> requests = friendService.getSentFriendRequests(userId);
        return new ApiResponse<>(requests, ApiResponseMessage.REQUEST_RETRIEVED);
    }

    @GetMapping("/receivedLists")
    public ApiResponse<List<FriendRequestResponseDto>> getReceivedFriendRequests(@RequestHeader Long userId) {
        List<FriendRequestResponseDto> requests = friendService.getReceivedFriendRequests(userId);
        return new ApiResponse<>(requests, ApiResponseMessage.REQUEST_RETRIEVED);
    }

    @PostMapping("/accept/{requestId}")
    public ApiResponse<Void> acceptFriendRequest(@PathVariable Long requestId) {
        friendService.acceptFriendRequest(requestId);
        return new ApiResponse<>(null, ApiResponseMessage.REQUEST_ACCEPTED);
    }

    @PostMapping("/reject/{requestId}")
    public ApiResponse<Void> rejectFriendRequest(@PathVariable Long requestId) {
        friendService.rejectFriendRequest(requestId);
        return new ApiResponse<>(null, ApiResponseMessage.REQUEST_REJECTED);
    }

    @GetMapping
    public ApiResponse<List<FriendResponseDto>> getFriends(@RequestHeader Long userId) {
        List<FriendResponseDto> friends = friendService.getFriends(userId);
        return new ApiResponse<>(friends, ApiResponseMessage.FRIENDS_RETRIEVED);
    }

    @DeleteMapping
    public ApiResponse<Void> deleteFriend(@RequestBody FriendDeleteDto friendDeleteDto) {
        friendService.deleteFriend(friendDeleteDto);
        return new ApiResponse<>(null, ApiResponseMessage.FRIEND_DELETED);
    }
}
