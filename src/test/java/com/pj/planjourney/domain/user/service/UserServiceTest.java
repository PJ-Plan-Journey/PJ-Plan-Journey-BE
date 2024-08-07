package com.pj.planjourney.domain.user.service;


import com.pj.planjourney.domain.blacklist.entity.BlackList;
import com.pj.planjourney.domain.blacklist.repository.BlackListRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import com.pj.planjourney.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlackListRepository blackListRepository;

    @Test
    public void testDeactivateUser() {
        // Create a user
        User user = new User();
        user.setEmail("test@example.com");
        user.setNickname("testuser");
        user.setPassword("password");
        userRepository.save(user);

        // Add user to blacklist
        BlackList blackList = new BlackList();
        blackList.setUser(user);
        blackList.setDeletedAt(LocalDateTime.now());
        blackList.setValidAt(LocalDateTime.now().plusMinutes(1));
        blackListRepository.save(blackList);

        // Deactivate user
        userService.deactivateUser("test@example.com");

        // Check if user is deactivated
        User deactivatedUser = userRepository.findByEmail("annoymous" + user.getId() + "@email.com").orElse(null);
        assertNotNull(deactivatedUser);

        // Check if user is removed from blacklist
        BlackList removedFromBlackList = blackListRepository.findById(blackList.getId()).orElse(null);
        assertNull(removedFromBlackList);
    }
}
