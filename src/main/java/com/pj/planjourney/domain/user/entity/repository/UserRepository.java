package com.pj.planjourney.domain.user.entity.repository;


import com.pj.planjourney.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
