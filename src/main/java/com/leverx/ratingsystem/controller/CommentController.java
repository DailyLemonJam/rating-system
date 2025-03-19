package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.comment.*;
import com.leverx.ratingsystem.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String keyword) {
        var commentDtos = commentService.getAllComments(userId, keyword);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
        var commentDto = commentService.getCommentById(id);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PostMapping("/seller/{id}")
    public ResponseEntity<CommentDto> createCommentOnSellerProfile(@PathVariable UUID id,
                                                                   @RequestBody CreateCommentRequest createCommentRequest) {
        var commentDto = commentService.createCommentOnSellerProfile(id, createCommentRequest);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> createCommentWithSellerProfile(@RequestBody @Valid CreateCommentWithSellerRequest request) {
        var commentDto = commentService.createCommentWithSellerProfile(request);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable UUID id,
                                                        @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        var commentDto = commentService.updateCommentById(id, updateCommentRequest);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id,
                                                  @Valid @RequestBody DeleteCommentRequest deleteCommentRequest) {
        commentService.deleteCommentById(id, deleteCommentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
