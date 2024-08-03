package com.pj.planjourney.domain.user.service;

import com.pj.planjourney.domain.user.dto.SignUpRequestDto;
import com.pj.planjourney.domain.user.dto.SignUpResponseDto;
import com.pj.planjourney.domain.user.dto.UpdateUserRequestDto;
import com.pj.planjourney.domain.user.dto.UpdateUserResponseDto;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import com.pj.planjourney.global.auth.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        //이메일 본인인증해야함
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        User user = new User(email, password, nickname);
        User savedUser = userRepository.save(user);
        return new SignUpResponseDto(
                savedUser.getId(),savedUser.getEmail(),savedUser.getNickname(),savedUser.getPassword());
    }


    public UpdateUserResponseDto updateNickname(UpdateUserRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        log.info(email);

        User user = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("user 없음"));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비번 틀림");
        }

        user.updateNickname(requestDto.getNickname());
        userRepository.save(user);

        return new UpdateUserResponseDto(user.getNickname());
    }
}
