package com.pj.planjourney.domain.plan.entity;

import com.pj.planjourney.domain.city.entity.City;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.like.entity.Like;
import com.pj.planjourney.domain.plan.dto.PlanCreateRequestDto;
import com.pj.planjourney.domain.plan.dto.PlanUpdateTitleRequestDto;
import com.pj.planjourney.domain.plandetail.entity.PlanDetail;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "plans")
public class Plan extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    private String title;

    private Boolean isPublished;

    private LocalDateTime publishedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "plan")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "plan")
    private List<PlanDetail> planDetails = new ArrayList<>();
    @OneToMany(mappedBy = "plan")
    private List<Like> likes = new ArrayList<>();

    public Plan(PlanCreateRequestDto planCreateRequestDto, User user) {
        this.title = planCreateRequestDto.getTitle();
        this.isPublished = planCreateRequestDto.getIsPublished();
        this.user = user;
    }

    public Plan(String title, User user) {
        this.title = title;
        this.isPublished = false;
        this.user = user;
    }

    public void updatePlan(PlanUpdateTitleRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
    }
    public Integer getLikeCount() {
        return likes.size();
    }
}
