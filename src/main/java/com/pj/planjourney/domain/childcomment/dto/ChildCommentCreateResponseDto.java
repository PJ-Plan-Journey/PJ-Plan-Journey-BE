package com.pj.planjourney.domain.childcomment.dto;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChildCommentCreateResponseDto {
    private Long id;
    private Long commentId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public ChildCommentCreateResponseDto(ChildComment savedChildComment) {
        this.id = savedChildComment.getId();
        this.commentId = savedChildComment.getComment().getId();
        this.content = savedChildComment.getContent();
        this.nickname = savedChildComment.getUser().getNickname();
        this.createdAt = savedChildComment.getCreatedAt();
    }
}
