package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.service.SellerProfileService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sellers")
public class SellerProfileController {
    private final SellerProfileService sellerService;

    @GetMapping("/top")
    public ResponseEntity<List<RatingDto>> getTopRatingSellers(@Min(AppConfiguration.MIN_GRADE) @Max(AppConfiguration.MAX_GRADE)
                                                                   @RequestParam(required = false, defaultValue = "1") double minRating,
                                                               @Min(AppConfiguration.MIN_GRADE) @Max(AppConfiguration.MAX_GRADE)
                                                               @RequestParam(required = false, defaultValue = "5") Double maxRating) {
        log.info("Min: {}, Max: {}", minRating, maxRating);
        var topRatings = sellerService.getTopRatingsSellers(minRating, maxRating);
        return new ResponseEntity<>(topRatings, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<List<RatingDto>> getSellerRatingsWithObjectsFromGame(@PathVariable Integer id) {
        var topRatings = sellerService.getSellerRatingsWithObjectsFromGame(id);
        return new ResponseEntity<>(topRatings, HttpStatus.OK);
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
