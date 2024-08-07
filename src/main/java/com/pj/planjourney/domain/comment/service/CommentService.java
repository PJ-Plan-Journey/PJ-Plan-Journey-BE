package com.pj.planjourney.domain.comment.service;

import com.pj.planjourney.domain.comment.dto.*;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.comment.repository.CommentRepository;
import com.pj.planjourney.domain.plan.entity.Plan;
import com.pj.planjourney.domain.plan.repository.PlanRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public CommentCreateResponseDto createComment(CommentCreateRequestDto requestDto, Long planId, Long userId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment(requestDto, plan, user);

        Comment savedComment = commentRepository.save(comment);
        return new CommentCreateResponseDto(savedComment);
    }

    public List<CommentListResponseDto> getAllComment(Long planId) {

        List<Comment> comments = commentRepository.findByPlanId(planId);

        return comments.stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());

    }

    public CommentUpdateResponseDto updateComment(Long planId, Long commentId, Long userId, CommentUpdateRequestDto requestDto) {
        Comment comment = getCommentById(commentId);

        if (!comment.getPlan().getId().equals(planId)) {
            throw new IllegalArgumentException("Plan ID mismatch");
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User ID mismatch");
        }
        comment.updateComment(requestDto);
        Comment updatedComment = commentRepository.save(comment);
        return new CommentUpdateResponseDto(updatedComment);
    }

    public void deleteComment(Long planId, Long commentId, Long userId) {
        Comment comment = getCommentById(commentId);

        if (!comment.getPlan().getId().equals(planId)) {
            throw new IllegalArgumentException("Plan ID mismatch");
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User ID mismatch");
        }
        commentRepository.delete(comment);

    }

    private Comment getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return comment;
    }
}