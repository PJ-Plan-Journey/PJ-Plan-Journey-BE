package com.pj.planjourney.domain.childcomment.dto;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChildCommentListResponseDto {
    private Long id;
    private Long commentId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public ChildCommentListResponseDto(ChildComment childComment) {
        this.id = childComment.getId();
        this.commentId = childComment.getComment().getId();
        this.content = childComment.getContent();
        this.nickname = childComment.getUser().getNickname();
        this.createdAt = childComment.getCreatedAt();
    }
}
