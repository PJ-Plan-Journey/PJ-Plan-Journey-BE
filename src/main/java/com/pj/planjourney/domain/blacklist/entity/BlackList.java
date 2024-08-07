package com.pj.planjourney.domain.blacklist.entity;

import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "black_lists") // s붙일지말지
public class BlackList {

    @Id
    @Column(name="black_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;


    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime validAt;

    public BlackList(User user, LocalDateTime deletedAt, LocalDateTime validAt) {
        this.user = user;
        this.deletedAt = deletedAt;
        this.validAt = validAt;
    }



}
