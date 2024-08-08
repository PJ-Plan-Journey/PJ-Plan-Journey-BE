package com.pj.planjourney.global.common.scheduler;

import com.pj.planjourney.domain.blacklist.entity.BlackList;
import com.pj.planjourney.domain.blacklist.repository.BlackListRepository;
import com.pj.planjourney.domain.blacklist.service.BlackListService;
import com.pj.planjourney.domain.user.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class BlackListScheduler {

    private final BlackListRepository blacklistRepository;
    private final UserService userService;
    private final BlackListService blackListService;

    // 생성자를 통해 의존성 주입
    public BlackListScheduler(BlackListRepository blacklistRepository, UserService userService, BlackListService blackListService) {
        this.blacklistRepository = blacklistRepository;
        this.userService = userService;
        this.blackListService = blackListService;
    }

    @Scheduled(fixedRate = 100000)
    @Transactional
    public void processBlackistedUsers() {
        log.info("Scheduler executed");

        LocalDateTime validAt = LocalDateTime.now();
        List<BlackList> expiredRequests = blacklistRepository.findByValidAtBefore(validAt);

        log.info("Found {} expired requests", expiredRequests.size());

        for (BlackList blacklist : expiredRequests) {
            String email = blacklist.getUser().getEmail();
            log.info("Processing blacklist for user with email: {}", email);
            userService.deactivateUser(email);  // 익명화 처리
//            blackListService.deleteUser(blacklist.getUser().getId());  // 블랙리스트에서 제거
            log.info("Blacklist for user with email {} processed and deleted", email);
        }
    }
}
