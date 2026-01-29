package com.portfolio.portfolio.features.users.dto;

import com.portfolio.portfolio.features.users.model.Roles;

import java.util.UUID;

public record UserDTOResponse(
        UUID id,
        String username,
        Roles roles
) {
}
