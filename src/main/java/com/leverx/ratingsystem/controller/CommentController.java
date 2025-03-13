package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.CommentDto;
import com.leverx.ratingsystem.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID id) {
        // TODO: get Comment by Id
    }

    // IMPORTANT: all comments are left by truly anonymous users (it basically means we don't know anything about them).
    // So the only way to be able for these users to delete/modify their comments - provide some kind of UUID of the comment
    // as response when it was created. Comment can be modified only once (protection against comment modification abusing).
    // This is not 100% reliable system, that would be better to have users account after all.
    // But as already said - anonymous users should be truly anonymous.

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable UUID id, @RequestBody CommentDto commentDto) {
        // TODO: update Comment by Id
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable UUID id) {
        // TODO: delete Comment by Id
    }
}
