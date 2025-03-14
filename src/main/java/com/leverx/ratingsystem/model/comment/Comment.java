package com.leverx.ratingsystem.model.comment;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id")
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String message;

    private int grade;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    private CommentStatus status;

}
