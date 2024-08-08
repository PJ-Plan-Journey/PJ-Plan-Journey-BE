package com.pj.planjourney.domain.blacklist.service;

import com.pj.planjourney.domain.blacklist.entity.BlackList;
import com.pj.planjourney.domain.blacklist.repository.BlackListRepository;
import com.pj.planjourney.domain.user.dto.DeactivateUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;


    // 블랙리스트에서 탈퇴 요청 철회
    public void cancelDeactivation(DeactivateUserRequestDto requestDto) {
        Optional<BlackList> checkedBlackList = blackListRepository.findByUserId(requestDto.getUser().getId());
        if (!checkedBlackList.isPresent()) {
            throw new IllegalArgumentException("블랙리스트에 해당 사용자가 없습니다.");
        }

        // 삭제 확인
        Optional<BlackList> deletedEntry = blackListRepository.findByUserId(requestDto.getUser().getId());

        if (deletedEntry.isPresent()) {
            throw new IllegalStateException("사용자 삭제 실패");
        }
    }

//    @Transactional
//    public void deleteUser(Long userId) {
//        try {
//            blackListRepository.deleteByUserId(userId);
//            log.info("BlackList entry for userId {} has been deleted.", userId);
//        } catch (Exception e) {
//            log.error("Error deleting BlackList entry for userId {}: {}", userId, e.getMessage());
//            throw e; // Optional: Rethrow or handle accordingly
//        }
//    }

}
