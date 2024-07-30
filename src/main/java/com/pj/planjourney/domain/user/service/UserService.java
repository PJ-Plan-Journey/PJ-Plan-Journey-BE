package com.pj.planjourney.domain.user.service;

import com.pj.planjourney.domain.user.dto.SignUpRequestDto;
import com.pj.planjourney.domain.user.dto.SignUpResponseDto;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    public SignUpRequestDto updateUser(SignUpRequestDto requestDto) {
        //토ㄴ큰이 일치하는 이메일 가져와야함
        //그이메일의 정보를 수정함
        //그 정보를 저장함
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {

        }
    }

}
