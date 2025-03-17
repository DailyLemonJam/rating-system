package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByUserIdAndStatus(UUID userId, CommentStatus status);
    List<Comment> findByStatus(CommentStatus status);
}
