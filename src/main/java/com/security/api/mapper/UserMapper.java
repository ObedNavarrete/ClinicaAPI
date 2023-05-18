package com.security.api.mapper;

import com.security.api.auth.base.User;
import com.security.api.dto.UserDTO;
import com.security.api.dto.UserLimDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
    List<User> toEntity(List<UserDTO> userDTOS);
    List<UserDTO> toDTO(List<User> users);
    UserLimDTO toUserLimDTO(User user);
    List<UserLimDTO> toUserLimDTO(List<User> users);
}
