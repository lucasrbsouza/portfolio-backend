package com.portfolio.portfolio.features.users.dto;

import com.portfolio.portfolio.features.users.model.Roles;

public record UserDTORequest(
        String username,
        String password,
        Roles roles
) {
}
