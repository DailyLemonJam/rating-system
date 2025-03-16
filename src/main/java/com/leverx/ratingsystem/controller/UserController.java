package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.service.CommentService;
import com.leverx.ratingsystem.service.GameObjectService;
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
    private final GameObjectService gameObjectService;
    // TODO: replace these services with UserService?

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
    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObjectDto>> getGameObjectsByUserId(@PathVariable UUID id) {
        var gameObjectDtos = gameObjectService.getAllGameObjectsByUserId(id);
        return new ResponseEntity<>(gameObjectDtos, HttpStatus.OK);
    }

    // TODO: Seller rating

}
