package com.portfolio.portfolio.features.users.service;

import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;

import java.util.UUID;

public interface UserService {
    UserDTOResponse create(UserDTORequest userDTO);
    UserDTOResponse findById(UUID id);
}
