package com.leverx.ratingsystem.mapper.rating;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.rating.Rating;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RatingMapper implements ModelDtoMapper<RatingDto, Rating> {

    @Override
    public RatingDto toDto(Rating rating) {
        return new RatingDto(rating.getUser().getId(),
                rating.getAverageRating(),
                rating.getTotalRatings());
    }

    @Override
    public Rating toModel(RatingDto ratingDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RatingDto> toDto(List<Rating> ratings) {
        var ratingDtos = new ArrayList<RatingDto>();
        for (var rating : ratings) {
            ratingDtos.add(toDto(rating));
        }
        return ratingDtos;
    }

    @Override
    public List<Rating> toModel(List<RatingDto> ratingDtos) {
        var ratings = new ArrayList<Rating>();
        for (var ratingDto : ratingDtos) {
            ratings.add(toModel(ratingDto));
        }
        return ratings;
    }
}
