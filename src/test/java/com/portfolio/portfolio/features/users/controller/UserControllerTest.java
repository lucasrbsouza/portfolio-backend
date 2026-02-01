package com.portfolio.portfolio.features.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.model.Roles;
import com.portfolio.portfolio.features.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String PATH = "/api/v1/users";
    public static final String PATH_WITH_ID = PATH + "/{id}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private UserDTORequest dtoRequest;
    private UserDTOResponse dtoResponse;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        dtoRequest = new UserDTORequest(
                "Lucas",
                "lucas123",
                Roles.ADMIN
        );
        dtoResponse = new UserDTOResponse(
                id,
                "Lucas",
                Roles.ADMIN
        );
    }

    @Test
    @DisplayName("deve criar usuario com sucesso e retornar 201 created")
    void deve_Criar_Usuario_Com_Sucesso() throws Exception {
        when(userService.create(dtoRequest)).thenReturn(dtoResponse);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.username").value("Lucas"))
                .andExpect(jsonPath("$.roles").value(Roles.ADMIN.toString()));
    }

    @Test
    void deve_Atualizar_Usuario_Com_Sucesso() throws Exception {

        UserDTOResponse newUser = new UserDTOResponse(
                id,
                "lucas",
                Roles.ADMIN
        );

        when(userService.update(id, dtoRequest)).thenReturn(newUser);

        mockMvc.perform(put(PATH_WITH_ID, id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.username").value("lucas"))
                .andExpect(jsonPath("$.roles").value(Roles.ADMIN.toString()));
    }

    @Test
    @DisplayName("Deve Listar Usuario por ID e retornar")
    void deveListarUsuarioPorID() throws Exception {
        when(userService.findById(id)).thenReturn(dtoResponse);

        mockMvc.perform(get(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.username").value("Lucas"))
                .andExpect(jsonPath("$.roles").value(Roles.ADMIN.toString()));

    }

    @Test
    void deleteUser() throws Exception {

        doNothing().when(userService).delete(id);

        mockMvc.perform(delete(PATH_WITH_ID, id))
                .andExpect(status().isNoContent());
    }
}