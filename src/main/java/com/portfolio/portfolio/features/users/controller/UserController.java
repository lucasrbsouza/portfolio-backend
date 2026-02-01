package com.portfolio.portfolio.features.users.controller;

import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService service;

    @Override
    @PostMapping
    public ResponseEntity<UserDTOResponse> createUser(@Valid @RequestBody UserDTORequest dtoRequest) {
        UserDTOResponse userResponse = service.create(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDTOResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTORequest dtoRequest) {
        UserDTOResponse updateUser = service.update(id, dtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> listUserByID(@PathVariable UUID id) {
        UserDTOResponse findUser = service.findById(id);
        return ResponseEntity.ok(findUser);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
