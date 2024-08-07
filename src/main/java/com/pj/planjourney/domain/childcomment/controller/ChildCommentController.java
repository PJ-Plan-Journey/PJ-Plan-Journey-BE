package com.pj.planjourney.domain.childcomment.controller;

import com.pj.planjourney.domain.childcomment.dto.*;
import com.pj.planjourney.domain.childcomment.service.ChildCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ChildCommentController {
    private final ChildCommentService childCommentService;

    @PostMapping("/{commentId}/child-comments")
    public ResponseEntity<ChildCommentCreateResponseDto> createChildComment(@PathVariable Long commentId, @RequestHeader Long userId, @RequestBody ChildCommentCreateRequestDto requestDto) {
        ChildCommentCreateResponseDto responseDto = childCommentService.createChildComment(requestDto, commentId, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/child-comments/{childCommentId}")
    public ResponseEntity<List<ChildCommentListResponseDto>> getAllChildComment(@PathVariable Long childCommentId) {
        List<ChildCommentListResponseDto> responseDto = childCommentService.getAllChildComment(childCommentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/child-comments/{childCommentId}")
    public ResponseEntity<ChildCommentUpdateResponseDto> updateChildComment(@PathVariable Long childCommentId, @RequestHeader Long userId, @RequestBody ChildCommentUpdateRequestDto requestDto) {
        ChildCommentUpdateResponseDto responseDto = childCommentService.updateChildComment(childCommentId, userId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @DeleteMapping("/child-comments/{childCommentId}")
    public ResponseEntity<Void> deleteChildComment(@PathVariable Long childCommentId, @RequestHeader Long userId) {
        childCommentService.deleteChildComment(childCommentId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
