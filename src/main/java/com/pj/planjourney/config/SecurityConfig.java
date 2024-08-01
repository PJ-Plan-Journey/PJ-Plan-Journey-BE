package com.pj.planjourney.config;

import com.pj.planjourney.auth.jwt.JwtLoginFilter;
import com.pj.planjourney.auth.jwt.JwtTokenFilter;
import com.pj.planjourney.auth.jwt.JwtTokenProvider;
import com.pj.planjourney.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration, UserDetailsServiceImpl userDetailsServiceImpl) throws Exception {
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/login/kakao").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers("/users/").permitAll() //회원가입 ( 회원정보 수정부분 주소 수정 필요)
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new JwtLoginFilter(jwtTokenProvider, userDetailsServiceImpl), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
