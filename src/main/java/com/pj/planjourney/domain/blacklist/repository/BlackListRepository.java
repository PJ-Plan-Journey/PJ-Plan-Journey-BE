package com.pj.planjourney.domain.blacklist.repository;

import com.pj.planjourney.domain.blacklist.entity.BlackList;
import com.pj.planjourney.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    void deleteByUser_Id(Long id);
    Optional<BlackList> findByUserId(Long id);

    List<BlackList> findByValidAtBefore(LocalDateTime tenDaysAgo);
}
