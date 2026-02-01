package com.portfolio.portfolio.features.users.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 50)
    @Setter
    private String username;

    @Setter
    private String password;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @Setter
    private Roles roles;

}
