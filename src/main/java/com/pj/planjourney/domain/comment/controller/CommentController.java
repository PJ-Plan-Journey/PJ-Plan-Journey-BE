package com.pj.planjourney.domain.comment.controller;

import com.pj.planjourney.domain.comment.dto.*;
import com.pj.planjourney.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans/{planId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentCreateResponseDto> createComment(@PathVariable Long planId, @RequestHeader Long userId, @RequestBody CommentCreateRequestDto requestDto) {
        CommentCreateResponseDto responseDto = commentService.createComment(requestDto, userId, planId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentListResponseDto>> getAllComment(@PathVariable Long planId) {
        List<CommentListResponseDto> responseDto = commentService.getAllComment(planId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@PathVariable Long planId, @PathVariable Long commentId, @RequestHeader Long userId, @RequestBody CommentUpdateRequestDto requestDto) {
        CommentUpdateResponseDto responseDto = commentService.updateComment(planId, commentId, userId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long planId, @PathVariable Long commentId, @RequestHeader Long userId) {
        commentService.deleteComment(planId, commentId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}