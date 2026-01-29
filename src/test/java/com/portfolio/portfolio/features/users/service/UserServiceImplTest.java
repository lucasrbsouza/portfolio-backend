package com.portfolio.portfolio.features.users.service;

import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.mapper.UserMapper;
import com.portfolio.portfolio.features.users.model.Roles;
import com.portfolio.portfolio.features.users.model.User;
import com.portfolio.portfolio.features.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @InjectMocks
    UserServiceImpl service;

    UserDTORequest request;
    User user;
    User savedUser;
    UserDTOResponse response;
    UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        request = new UserDTORequest(
                "Lucas",
                "lucas123",
                Roles.ADMIN
        );

        user = new User();
        user.setUsername("Lucas");
        user.setPassword("lucas123");
        user.setRoles(Roles.ADMIN);

        savedUser = new User();
        savedUser.setId(userId);
        savedUser.setUsername("Lucas");
        savedUser.setPassword("lucas123");
        savedUser.setRoles(Roles.ADMIN);

        response = new UserDTOResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRoles()
        );
    }

    @DisplayName("deve criar usuario com sucesso")
    @Test
    void deve_criar_usuario_com_sucesso() {
        // given
        when(mapper.userDTOToUser(request)).thenReturn(user);
        when(repository.save(user)).thenReturn(savedUser);
        when(mapper.userToUserDTOResponse(savedUser)).thenReturn(response);

        // when
        UserDTOResponse result = service.create(request);

        // then
        assertNotNull(result);
        assertEquals("Lucas", result.username());
        assertEquals(Roles.ADMIN, result.roles());

        verify(mapper).userDTOToUser(request);
        verify(repository).save(user);
        verify(mapper).userToUserDTOResponse(savedUser);
    }

    @DisplayName("deve listar usuario por ID")
    @Test
    void deve_listar_usuario_por_id() {
        // given
        when(repository.findById(userId)).thenReturn(Optional.of(savedUser));
        when(mapper.userToUserDTOResponse(savedUser)).thenReturn(response);

        // when
        UserDTOResponse result = service.findById(userId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("Lucas", result.username());
        assertEquals(Roles.ADMIN, result.roles());

        verify(repository).findById(userId);
        verify(repository, never()).save(any());
    }

    @DisplayName("deve atualizar usuario com sucesso")
    @Test
    void deve_atualizar_usuario_com_sucesso() {
        // given
        UserDTORequest updateRequest = new UserDTORequest(
                "Lucas",
                "novaSenha",
                Roles.ADMIN
        );

        when(repository.findById(userId)).thenReturn(Optional.of(savedUser));
        when(repository.save(savedUser)).thenReturn(savedUser);
        when(mapper.userToUserDTOResponse(savedUser)).thenReturn(response);

        // when
        UserDTOResponse update = service.update(userId, updateRequest);

        // then
        assertNotNull(update);
        assertEquals(userId, update.id());
        assertEquals("Lucas", update.username());

        verify(repository).findById(userId);
        verify(repository).save(savedUser);
    }

    @DisplayName("deve excluir usuario com sucesso")
    @Test
    void deve_excluir_usuario_com_sucesso() {
        //given
        //when
        when(repository.findById(userId)).thenReturn(Optional.of(savedUser));
        doNothing().when(repository).deleteById(userId);
        //Then
        service.delete(userId);


        verify(repository).findById(userId);
        verify(repository).deleteById(userId);
        verify(repository, never()).save(any());
    }
}
