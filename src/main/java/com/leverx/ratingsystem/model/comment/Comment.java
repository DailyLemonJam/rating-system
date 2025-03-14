package com.leverx.ratingsystem.model.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(AppConfiguration.MIN_GRADE)
    @Max(AppConfiguration.MAX_GRADE)
    private int grade;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    private CommentStatus status;

}
