package com.leverx.ratingsystem.model.comment;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String message;

    private int grade;

    private LocalDateTime createdAt;

    @ManyToOne
    private User seller;

    private CommentStatus status;

}
