package com.pj.planjourney.domain.comment.dto;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import com.pj.planjourney.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentCreateResponseDto {
    private Long id;
    private String content;
    private Long userId;
    private Long planId;
    private LocalDateTime createdAt;
    private List<ChildComment> childComment;

    public CommentCreateResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.planId = comment.getPlan().getId();
        this.createdAt = comment.getCreatedAt();
    }
}
