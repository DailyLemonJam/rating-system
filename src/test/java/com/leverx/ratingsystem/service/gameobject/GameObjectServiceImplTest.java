package com.leverx.ratingsystem.service.gameobject;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.game.Game;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import com.leverx.ratingsystem.repository.GameRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import com.leverx.ratingsystem.service.gameobject.GameObjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameObjectServiceImplTest {

    @Mock
    private GameObjectRepository gameObjectRepository;

    @Mock
    private ModelDtoMapper<GameObjectDto, GameObject> gameObjectMapper;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameObjectServiceImpl gameObjectService;

    private final UUID gameObjectId = UUID.randomUUID();
    private final Integer gameId = 1;

    @Test
    void getAllGameObjectsByGame_success() {
        var game = new Game();
        var gameObject = new GameObject();
        List<GameObject> gameObjects = List.of(gameObject);
        var gameObjectDto = new GameObjectDto("No, not my title", UUID.randomUUID(), "desc",
                Instant.now(), Instant.now(), UUID.randomUUID());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameObjectRepository.findAllByGame(game)).thenReturn(gameObjects);
        when(gameObjectMapper.toDto(gameObjects)).thenReturn(List.of(gameObjectDto));

        var result = gameObjectService.getAllGameObjectsByGame(gameId);

        assertEquals(1, result.size());
        verify(gameRepository).findById(gameId);
        verify(gameObjectRepository).findAllByGame(game);
    }

    @Test
    void getGameObjectById_success() {
        var gameObject = new GameObject();
        gameObject.setId(gameObjectId);
        gameObject.setUpdatedAt(Instant.now());
        gameObject.setTitle("Title");
        gameObject.setDescription("Description");
        gameObject.setUser(new User());
        var gameObjectDto = new GameObjectDto("Yes, my title", UUID.randomUUID(), "desc",
                Instant.now(), Instant.now(), UUID.randomUUID());

        when(gameObjectRepository.findById(gameObjectId)).thenReturn(Optional.of(gameObject));
        when(gameObjectMapper.toDto(gameObject)).thenReturn(gameObjectDto);

        var result = gameObjectService.getGameObjectById(gameObjectId);

        assertNotNull(result);
        verify(gameObjectRepository).findById(gameObjectId);
    }

    @Test
    void createGameObject_success() {
        var createRequest = new CreateGameObjectRequest("title", "description", gameId);
        var principal = mock(Principal.class);
        var game = new Game();
        var user = new User();
        var gameObject = new GameObject();
        var gameObjectDto = new GameObjectDto("Yes, my title", UUID.randomUUID(), "desc",
                Instant.now(), Instant.now(), UUID.randomUUID());

        when(principal.getName()).thenReturn("username");
        when(gameRepository.findById(createRequest.gameId())).thenReturn(Optional.of(game));
        when(userRepository.findByName("username")).thenReturn(Optional.of(user));
        when(gameObjectRepository.save(any(GameObject.class))).thenReturn(gameObject);
        when(gameObjectMapper.toDto(gameObject)).thenReturn(gameObjectDto);

        var result = gameObjectService.createGameObject(createRequest, principal);

        assertNotNull(result);
        verify(gameRepository).findById(createRequest.gameId());
        verify(userRepository).findByName("username");
        verify(gameObjectRepository).save(any(GameObject.class));
    }

    @Test
    void updateGameObjectById_success() {
        var updateRequest = new UpdateGameObjectRequest("new title", "new description");
        var principal = mock(Principal.class);
        var gameObject = new GameObject();
        var user = new User();
        user.setName("username");
        gameObject.setUser(user);
        var gameObjectDto = new GameObjectDto("Yes, my title", UUID.randomUUID(), "desc",
                Instant.now(), Instant.now(), UUID.randomUUID());

        when(principal.getName()).thenReturn("username");
        when(gameObjectRepository.findById(gameObjectId)).thenReturn(Optional.of(gameObject));
        when(gameObjectMapper.toDto(gameObject)).thenReturn(gameObjectDto);

        var result = gameObjectService.updateGameObjectById(gameObjectId, updateRequest, principal);

        assertNotNull(result);
        verify(gameObjectRepository).findById(gameObjectId);
        verify(gameObjectRepository).save(gameObject);
        verify(gameObjectMapper).toDto(gameObject);
    }

    @Test
    void deleteGameObjectById_success() {
        var principal = mock(Principal.class);
        var gameObject = new GameObject();
        var user = new User();
        user.setName("username");
        gameObject.setUser(user);

        when(principal.getName()).thenReturn("username");
        when(gameObjectRepository.findById(gameObjectId)).thenReturn(Optional.of(gameObject));

        gameObjectService.deleteGameObjectById(gameObjectId, principal);

        verify(gameObjectRepository).findById(gameObjectId);
        verify(gameObjectRepository).delete(gameObject);
    }
}
