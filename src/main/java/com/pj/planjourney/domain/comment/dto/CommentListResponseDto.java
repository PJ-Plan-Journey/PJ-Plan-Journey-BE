package com.pj.planjourney.domain.comment.dto;

import com.pj.planjourney.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentListResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private Long planId;
    private LocalDateTime createdAt;

    public CommentListResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.planId = comment.getPlan().getId();
        this.createdAt = comment.getCreatedAt();
    }
}
