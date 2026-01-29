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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


    @BeforeEach
    void setUp() {
        request = new UserDTORequest(
                "Lucas",
                "lucas123",
                Roles.ADMIN
        );
        user = new User();
        user.setUsername("Lucas");
        user.setPassword("lucas123");
        user.setRoles(Roles.ADMIN);

    }

    @DisplayName("deve criar usuario com sucesso")
    @Test
    void deve_criar_usuario_com_sucesso() {
        // given
        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setUsername("Lucas");
        savedUser.setPassword("lucas123");
        savedUser.setRoles(Roles.ADMIN);

        UserDTOResponse response = new UserDTOResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRoles()
        );

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

    @DisplayName("deve listar usuarios por ID")
    @Test
    void deve_listar_usuarios_por_id() {
        UUID userId = UUID.randomUUID();
        //given
        User savedUser = new User();
        savedUser.setId(userId);
        savedUser.setUsername("Lucas");
        savedUser.setPassword("lucas");
        savedUser.setRoles(Roles.ADMIN);

        UserDTOResponse userDTOResponse = new UserDTOResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRoles()
        );
        //when
        when(repository.findById(userId)).thenReturn(Optional.of(savedUser));
        when(mapper.userToUserDTOResponse(savedUser)).thenReturn(userDTOResponse);
        //Then
        UserDTOResponse result = service.findById(savedUser.getId());

        assertEquals(savedUser.getId(), result.id());
        assertEquals(savedUser.getUsername(), result.username());
        assertEquals(savedUser.getRoles(), result.roles());

        verify(repository, times(1)).findById(userId);
        verify(repository, never()).save(savedUser);
    }

}