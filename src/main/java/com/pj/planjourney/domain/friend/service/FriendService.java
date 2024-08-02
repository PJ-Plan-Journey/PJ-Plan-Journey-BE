package com.pj.planjourney.domain.friend.service;

import com.pj.planjourney.domain.friend.dto.FriendDeleteDto;
import com.pj.planjourney.domain.friend.dto.FriendRequestResponseDto;
import com.pj.planjourney.domain.friend.dto.FriendRequestSendDto;
import com.pj.planjourney.domain.friend.dto.FriendResponseDto;
import com.pj.planjourney.domain.friend.entity.Friend;
import com.pj.planjourney.domain.friend.repository.FriendRepository;
import com.pj.planjourney.domain.friendrequest.entity.FriendRequest;
import com.pj.planjourney.domain.friendrequest.repository.FriendRequestRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import com.pj.planjourney.global.common.exception.BusinessLogicException;
import com.pj.planjourney.global.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    public void sendFriendRequest(FriendRequestSendDto requestCreateDto, Long userId) {
        User requester = getUserById(userId);
        User receiver = userRepository.findByEmail(requestCreateDto.getReceiverEmail())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        if (friendRequestRepository.findBySenderAndReceiver(requester, receiver) != null) {
            throw new BusinessLogicException(ExceptionCode.REQUEST_ALREADY_SENT);
        }

        FriendRequest friendRequest = new FriendRequest(requester, receiver);
        friendRequestRepository.save(friendRequest);
    }


    public List<FriendRequestResponseDto> getSentFriendRequests(Long userId) {
        User user = getUserById(userId);
        return friendRequestRepository.findBySender(user).stream()
                .map(FriendRequestResponseDto::new).toList();
    }

    @Transactional
    public void acceptFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REQUEST_NOT_FOUND));

        friendRequest.accept();

        User requester = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();

        Friend friend1 = new Friend(requester, receiver);
        Friend friend2 = new Friend(receiver, requester);

        friendRepository.save(friend1);
        friendRepository.save(friend2);
    }

    @Transactional
    public void rejectFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REQUEST_NOT_FOUND));

        friendRequest.reject();
    }

    public List<FriendResponseDto> getFriends(Long userId) {
        User user = getUserById(userId);
        return friendRepository.findByUser(user).stream()
                .map(FriendResponseDto::new).toList();
    }

    @Transactional
    public void deleteFriend(Long userId, FriendDeleteDto friendDeleteDto) {
        User user = getUserById(userId);
        User friend = userRepository.findById(friendDeleteDto.getFriendId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Friend friendship1 = friendRepository.findByUserAndFriend(user, friend);
        Friend friendship2 = friendRepository.findByUserAndFriend(friend, user);

        if (friendship1 != null) {
            friendRepository.delete(friendship1);
        }

        if (friendship2 != null) {
            friendRepository.delete(friendship2);
        }
    }

    public List<FriendRequestResponseDto> getReceivedFriendRequests(Long userId) {
        User user = getUserById(userId);
        return friendRequestRepository.findByReceiver(user).stream()
                .map(FriendRequestResponseDto::new).toList();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

}
