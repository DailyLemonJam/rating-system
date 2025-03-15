package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable UUID id,
                                                    @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        var commentDto = commentService.createComment(id, createCommentRequest);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable UUID id) {
        var commentsDto = commentService.getAllCommentsByUserId(id);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    // TODO: list of objects Seller has

}
