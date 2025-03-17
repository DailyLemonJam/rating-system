package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GameObjectRepository gameObjectRepository;
    private final RatingRepository ratingRepository;
    private final ModelDtoMapper<CommentDto, Comment> commentMapper;
    private final ModelDtoMapper<GameObjectDto, GameObject> gameObjectMapper;
    private final ModelDtoMapper<RatingDto, Rating> ratingMapper;

    @Transactional
    public CommentDto createCommentOnSellerProfile(UUID userId, CreateCommentRequest createCommentRequest) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var comment = Comment.builder()
                .message(createCommentRequest.message())
                .grade(createCommentRequest.grade())
                .user(user)
                .createdAt(Instant.now())
                .status(CommentStatus.PENDING)
                .editCount(0)
                .build();
        var newComment = commentRepository.save(comment);
        return commentMapper.toDto(newComment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        var comments = commentRepository.findAllByUser_IdAndStatus(userId, CommentStatus.APPROVED);
        return commentMapper.toDto(comments);
    }

    @Transactional(readOnly = true)
    public List<GameObjectDto> getAllGameObjectsByUserId(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var gameObjects = gameObjectRepository.findAllByUser(user);
        return gameObjectMapper.toDto(gameObjects);
    }

    @Transactional(readOnly = true)
    public RatingDto getRatingByUserId(UUID userId) {
        var rating = ratingRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User rating not found"));
        return ratingMapper.toDto(rating);
    }
}
