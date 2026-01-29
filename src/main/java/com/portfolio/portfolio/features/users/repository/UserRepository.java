package com.portfolio.portfolio.features.users.repository;

import com.portfolio.portfolio.features.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
