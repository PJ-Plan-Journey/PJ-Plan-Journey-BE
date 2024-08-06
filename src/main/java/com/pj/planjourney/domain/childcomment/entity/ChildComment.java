package com.pj.planjourney.domain.childcomment.entity;

import com.pj.planjourney.domain.childcomment.dto.ChildCommentCreateRequestDto;
import com.pj.planjourney.domain.childcomment.dto.ChildCommentUpdateRequestDto;
import com.pj.planjourney.domain.comment.dto.CommentCreateRequestDto;
import com.pj.planjourney.domain.comment.dto.CommentUpdateRequestDto;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "child_comments")
@NoArgsConstructor
public class ChildComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_comment_id")
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ChildComment(ChildCommentCreateRequestDto requestDto, Comment comment, User user) {
        this.content = requestDto.getContent();
        this.user = user;
        this.comment = comment;
    }

    public void updateChildComment(ChildCommentUpdateRequestDto requestDto) {
        if (requestDto.getContent() != null) {
            this.content = requestDto.getContent();
        }
    }
}