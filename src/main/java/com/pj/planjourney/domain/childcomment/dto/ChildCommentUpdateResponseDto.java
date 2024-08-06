package com.pj.planjourney.domain.childcomment.dto;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChildCommentUpdateResponseDto {
    private Long id;
    private Long commentId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public ChildCommentUpdateResponseDto(ChildComment childComment) {
        this.createdAt = childComment.getCreatedAt();
        this.nickname = childComment.getUser().getNickname();
        this.content = childComment.getContent();
        this.commentId = childComment.getComment().getId();
        this.id = childComment.getId();
    }
}
