package com.portfolio.portfolio.features.users.service;

import com.portfolio.portfolio.common.excpetion.custom.UsuarioNaoEcontradoException;
import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.mapper.UserMapper;
import com.portfolio.portfolio.features.users.model.User;
import com.portfolio.portfolio.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDTOResponse create(UserDTORequest userDTO) {

        User user = mapper.userDTOToUser(userDTO);
        User saved = repository.save(user);

        return mapper.userToUserDTOResponse(saved);

    }

    @Override
    public UserDTOResponse findById(UUID id) {
        User byId = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEcontradoException("Usuario não econtrado"));
        return mapper.userToUserDTOResponse(byId);
    }

    @Override
    public UserDTOResponse update(UUID id, UserDTORequest userDTO) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEcontradoException("Usuario não econtrado"));

        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setRoles(userDTO.roles());

        User saved = repository.save(user);
        return mapper.userToUserDTOResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEcontradoException("Usuario não encontrado"));

        repository.deleteById(user.getId());
    }
}
