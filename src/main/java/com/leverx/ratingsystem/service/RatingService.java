package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.exception.UserRatingNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final ModelDtoMapper<RatingDto, Rating> ratingMapper;

    @Transactional
    public void recalculateRatingByUserId(UUID userId) {
        // var comments = commentRepository.findAllByUserId(userId);
        // TODO: recalculate
    }

    @Transactional(readOnly = true)
    public RatingDto getRatingByUserId(UUID userId) {
        var ratingDto = ratingRepository.findByUserId(userId)
                .orElseThrow(() -> new UserRatingNotFoundException("Can't find rating of this user"));
        return ratingMapper.toDto(ratingDto);
    }

}
