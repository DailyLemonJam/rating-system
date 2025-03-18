package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.TopSellersArgsRequest;
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
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/top")
    public ResponseEntity<List<RatingDto>> getTopRatingSellers(@Valid @RequestBody TopSellersArgsRequest request) {
        var topRatings = sellerService.getTopRatingsSellers(request);
        return new ResponseEntity<>(topRatings, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<List<RatingDto>> getSellerRatingsWithObjectsFromGame(@PathVariable Integer id) {
        var topRatings = sellerService.getSellerRatingsWithObjectsFromGame(id);
        return new ResponseEntity<>(topRatings, HttpStatus.OK);
    }

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
