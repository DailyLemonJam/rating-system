package com.leverx.ratingsystem.service.profile;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.exception.GameNotFoundException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerProfileServiceImpl implements SellerProfileService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GameObjectRepository gameObjectRepository;
    private final RatingRepository ratingRepository;
    private final ModelDtoMapper<CommentDto, Comment> commentMapper;
    private final ModelDtoMapper<GameObjectDto, GameObject> gameObjectMapper;
    private final ModelDtoMapper<RatingDto, Rating> ratingMapper;
    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RatingDto> getTopRatingsSellers(double minRating, double maxRating) {
        if (minRating > maxRating) {
            throw new IllegalArgumentException("Min Rating must be greater than Max Rating");
        }
        var topRatings = ratingRepository
                .findAllByAverageRatingBetweenOrderByAverageRatingDesc(minRating, maxRating);
        return ratingMapper.toDto(topRatings);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RatingDto> getSellerRatingsWithObjectsFromGame(Integer gameId) {
        var game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Can't find game with Id " + gameId));
        var usersWithGameObjectsFromGame = gameObjectRepository.findAllUsersByGame(game);
        var userRatingsWithObjectsFromGameDto = new ArrayList<RatingDto>();
        for (var user : usersWithGameObjectsFromGame) {
            ratingRepository.findByUser(user)
                    .ifPresent(rating ->
                            userRatingsWithObjectsFromGameDto.add(ratingMapper.toDto(rating)));
        }
        return userRatingsWithObjectsFromGameDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        var comments = commentRepository.findAllByUser_IdAndStatus(userId, CommentStatus.APPROVED);
        return commentMapper.toDto(comments);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GameObjectDto> getAllGameObjectsByUserId(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var gameObjects = gameObjectRepository.findAllByUser(user);
        return gameObjectMapper.toDto(gameObjects);
    }

    @Transactional(readOnly = true)
    @Override
    public RatingDto getRatingByUserId(UUID userId) {
        var rating = ratingRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserNotFoundException("User rating not found"));
        return ratingMapper.toDto(rating);
    }
}
