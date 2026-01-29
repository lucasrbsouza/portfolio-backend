package com.portfolio.portfolio.features.users.service;

import com.portfolio.portfolio.common.excpetion.custom.UsuarioNaoEcontradoException;
import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.mapper.UserMapper;
import com.portfolio.portfolio.features.users.model.User;
import com.portfolio.portfolio.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

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
                .orElseThrow(()-> new UsuarioNaoEcontradoException("Usuario não econtrado"));
        return mapper.userToUserDTOResponse(byId);
    }
}
