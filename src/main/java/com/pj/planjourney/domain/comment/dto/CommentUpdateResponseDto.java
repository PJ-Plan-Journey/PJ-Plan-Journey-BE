package com.pj.planjourney.domain.comment.dto;

import com.pj.planjourney.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private Long planId;
    private LocalDateTime createAt;

    public CommentUpdateResponseDto(Comment updatedComment) {
        this.id = updatedComment.getId();
        this.content = updatedComment.getContent();
        this.nickname = updatedComment.getUser().getNickname();
        this.planId = updatedComment.getPlan().getId();
        this.createAt = updatedComment.getCreatedAt();
    }
}
