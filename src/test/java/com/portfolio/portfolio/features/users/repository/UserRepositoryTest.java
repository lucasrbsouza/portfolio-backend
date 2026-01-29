package com.portfolio.portfolio.features.users.repository;

import com.portfolio.portfolio.features.users.model.Roles;
import com.portfolio.portfolio.features.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    User user;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        //given
        user = new User();
        user.setUsername("Lucas");
        user.setPassword("lucas123");
        user.setRoles(Roles.ADMIN);
    }

    @DisplayName("Deve Salvar No Banco Quando O Objeto For Criado")
    @Test
    void testUserRepository_Deve_SalvarNoBanco_Quando_OObjetoForCriado() {
        //when
        User saved = repository.save(user);
        //Then
        assertNotNull(saved);
        assertFalse(user.getId().toString().isEmpty());
    }


    @DisplayName("Deve retornar Lista de pessoas salvas")
    @Test
    void testUserRepository_Deve_Retornar_lista_De_Pessoas_Salvas() {
        //given
        User user2 = new User();
        user2.setUsername("Fulano");
        user2.setPassword("Fulano123");
        user2.setRoles(Roles.ADMIN);

        repository.save(user);
        repository.save(user2);
        //when
        List<User> users = repository.findAll();
        //Then
        assertNotNull(users);
        assertEquals(2, users.size());

    }

    @DisplayName("Deve retornar usuario quando listar por ID")
    @Test
    void testUserRepository_Deve_retornar_usuario_Quando_listar_por_id() {
        repository.save(user);
        //when
        User userById = repository.findById(user.getId()).orElseThrow();
        //Then
        assertEquals(user.getId(), userById.getId(), () -> "Usuario não econtrado");
    }

}