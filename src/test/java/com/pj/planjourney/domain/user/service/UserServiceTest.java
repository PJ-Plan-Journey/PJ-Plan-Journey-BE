package com.pj.planjourney.domain.user.service;

import com.pj.planjourney.domain.user.dto.SignUpRequestDto;
import com.pj.planjourney.domain.user.dto.SignUpResponseDto;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    User user;
    SignUpResponseDto responseDto = null;


    @Test
    @DisplayName("회원가입")
    void SignUpTest() {
        // given
        String email = "pj1@email.com";
        String password = "pj1234";
        String nickname = "짱PJ1";


        SignUpRequestDto requestDto = new SignUpRequestDto(
                email,
                password,
                nickname
        );

        // when
        SignUpResponseDto responseDto = userService.signUp(requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals(email, responseDto.getEmail());
        assertEquals(nickname, responseDto.getNickname());

        Optional<User> savedUserOptional = userRepository.findByEmail(email);
        assertTrue(savedUserOptional.isPresent());

        User savedUser = savedUserOptional.get();
        assertEquals(email, savedUser.getEmail());
        assertEquals(nickname, savedUser.getNickname());
        assertTrue(encoder.matches(password, savedUser.getPassword())); // Ensure the password is encoded and saved
    }
}
