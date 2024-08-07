package com.pj.planjourney.domain.childcomment.repository;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildCommentRepository extends JpaRepository<ChildComment, Long> {

    List<ChildComment> findByCommentId(Long commentId);
}
