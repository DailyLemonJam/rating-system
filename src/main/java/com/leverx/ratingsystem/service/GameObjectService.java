package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.exception.GameNotFoundException;
import com.leverx.ratingsystem.exception.GameObjectNotFoundException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.GameObjectMapper;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GameObjectService {
    private final GameObjectRepository gameObjectRepository;
    private final GameService gameService;
    private final UserService userService;
    private final GameObjectMapper gameObjectMapper;

    public List<GameObjectDto> getAllGameObjectsByUserId(UUID userId) {
        var seller = userService.findById(userId).filter(user ->
                        user.getRoles().stream().anyMatch(role ->
                                role.getName().equals(AppConfiguration.ROLE_USER)))
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        var sellerGameObjects = gameObjectRepository.findByUser(seller);
        return gameObjectMapper.toDto(sellerGameObjects);
    }

    public GameObjectDto getGameObjectById(UUID gameObjectId) {
        var gameObject = gameObjectRepository
                .findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        return gameObjectMapper.toDto(gameObject);
    }

    public GameObjectDto createGameObject(CreateGameObjectRequest createGameObjectRequest) {
        var game = gameService.findById(createGameObjectRequest.gameId())
                .orElseThrow(() -> new GameNotFoundException("Can't find game"));
        var instantNow = Instant.now();
        var gameObject = GameObject.builder()
                .title(createGameObjectRequest.title())
                .description(createGameObjectRequest.description())
                .game(game)
                .createdAt(instantNow)
                .updatedAt(instantNow)
                // TODO: User
                .build();
        var newGameObject = gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(newGameObject);
    }

    public GameObjectDto updateGameObjectById(UUID gameObjectId, UpdateGameObjectRequest updateGameObjectRequest) {
        var gameObject = gameObjectRepository.findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        gameObject.setTitle(updateGameObjectRequest.newTitle());
        gameObject.setDescription(updateGameObjectRequest.newDescription());
        gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(gameObject);
    }

    public void deleteGameObjectById(UUID gameObjectId) {
        if (gameObjectRepository.existsById(gameObjectId)) {
            gameObjectRepository.deleteById(gameObjectId);
            return;
        }
        throw new GameObjectNotFoundException("Can't find gameobject with id " + gameObjectId);
    }

}
