package com.portfolio.portfolio.features.users.controller;

import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserControllerDocs {

    ResponseEntity<UserDTOResponse> createUser(UserDTORequest dtoRequest);
    ResponseEntity<UserDTOResponse> updateUser(UUID id, UserDTORequest dtoRequest);
    ResponseEntity<UserDTOResponse> listUserByID(UUID id);
    ResponseEntity<Void> deleteUser(UUID id);
}
