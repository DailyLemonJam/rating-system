package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByUser_IdAndStatus(UUID userId, CommentStatus status);
    List<Comment> findAllByStatus(CommentStatus status);
    List<Comment> findAllByUser_Id(UUID userId);
    Optional<Comment> findByIdAndStatus(UUID userId, CommentStatus status);

    List<Comment> findAllByUser_Id(UUID userId, Limit limit);
}
