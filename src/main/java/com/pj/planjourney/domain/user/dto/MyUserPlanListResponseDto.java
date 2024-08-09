package com.pj.planjourney.domain.user.dto;

import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyUserPlanListResponseDto {

    private Long id;
    private String nickname;
    private String cityname;
    private String title;
    private Boolean isPublished;
    private LocalDateTime createAt;
    private LocalDateTime publishedAt;
    private Integer likeCount;
    private Integer commentCount;


    public MyUserPlanListResponseDto(Long id, String nickname, String name, String title, Boolean isPublished, LocalDateTime createdAt, LocalDateTime publishedAt, Integer likeCount, Integer count) {
        this.id = id;
        this.nickname = nickname;
        this.cityname = name;
        this.title = title;
        this.isPublished = isPublished;
        this.createAt = createdAt;
        this.publishedAt = publishedAt;
        this.likeCount = likeCount;
        this.commentCount = count;
    }
}
