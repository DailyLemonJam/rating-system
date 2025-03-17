package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createCommentToSeller(@PathVariable UUID id,
                                                            @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        var commentDto = sellerService.createCommentOnSellerProfile(id, createCommentRequest);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsBySellerId(@PathVariable UUID id) {
        var commentsDto = sellerService.getCommentsByUserId(id);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObjectDto>> getGameObjectsBySellerId(@PathVariable UUID id) {
        var gameObjectDtos = sellerService.getAllGameObjectsByUserId(id);
        return new ResponseEntity<>(gameObjectDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<RatingDto> getRatingBySellerId(@PathVariable UUID id) {
        var rating = sellerService.getRatingByUserId(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

}
