package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.exception.GameNotFoundException;
import com.leverx.ratingsystem.exception.GameObjectNotFoundException;
import com.leverx.ratingsystem.exception.NotAllowedToUpdateGameObjectException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import com.leverx.ratingsystem.repository.GameRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GameObjectService {
    private final GameObjectRepository gameObjectRepository;
    private final ModelDtoMapper<GameObjectDto, GameObject> gameObjectMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameObjectDto getGameObjectById(UUID gameObjectId) {
        var gameObject = gameObjectRepository.findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        return gameObjectMapper.toDto(gameObject);
    }

    public GameObjectDto createGameObject(CreateGameObjectRequest createGameObjectRequest, Principal principal) {
        var game = gameRepository.findById(createGameObjectRequest.gameId())
                .orElseThrow(() -> new GameNotFoundException("Can't find game"));
        var user = userRepository.findByName(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("Somehow can't find your profile..."));
        var instantNow = Instant.now();
        var gameObject = GameObject.builder()
                .title(createGameObjectRequest.title())
                .description(createGameObjectRequest.description())
                .game(game)
                .createdAt(instantNow)
                .updatedAt(instantNow)
                .user(user)
                .build();
        var newGameObject = gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(newGameObject);
    }

    public GameObjectDto updateGameObjectById(UUID gameObjectId, UpdateGameObjectRequest updateGameObjectRequest, Principal principal) {
        var gameObject = gameObjectRepository.findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        if (!gameObject.getUser().getName().equals(principal.getName())) {
            throw new NotAllowedToUpdateGameObjectException("This item doesn't belong to your account");
        }
        gameObject.setTitle(updateGameObjectRequest.newTitle());
        gameObject.setDescription(updateGameObjectRequest.newDescription());
        gameObject.setUpdatedAt(Instant.now());
        gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(gameObject);
    }

    public void deleteGameObjectById(UUID gameObjectId, Principal principal) {
        var gameObject = gameObjectRepository.findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object"));
        if (!gameObject.getUser().getName().equals(principal.getName())) {
            throw new NotAllowedToUpdateGameObjectException("This item doesn't belong to your account");
        }
        gameObjectRepository.delete(gameObject);
    }

}
