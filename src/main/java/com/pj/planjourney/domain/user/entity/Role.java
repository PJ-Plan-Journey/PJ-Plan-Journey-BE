package com.pj.planjourney.domain.user.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Role {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }

}
