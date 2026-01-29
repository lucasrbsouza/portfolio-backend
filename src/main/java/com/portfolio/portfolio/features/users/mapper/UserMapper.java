package com.portfolio.portfolio.features.users.mapper;

import com.portfolio.portfolio.features.users.dto.UserDTORequest;
import com.portfolio.portfolio.features.users.dto.UserDTOResponse;
import com.portfolio.portfolio.features.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUser(UserDTORequest userDTO);

    UserDTOResponse userToUserDTOResponse(User user);

}
