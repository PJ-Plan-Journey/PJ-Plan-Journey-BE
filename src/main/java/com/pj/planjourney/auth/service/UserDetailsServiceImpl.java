package com.pj.planjourney.auth.service;

import com.pj.planjourney.auth.jwt.JwtTokenProvider;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    private UserDetails createUserDetails(User user) {
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        // UserDetails 객체 생성
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getEmail()),
                user.getPassword(),
                authorities
        );

    }


}

