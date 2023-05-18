package com.security.api.mapper;

import com.security.api.dto.ProfileDTO;
import com.security.api.dto.ProfileLimDTO;
import com.security.api.entity.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toEntity(ProfileDTO dto);

    ProfileDTO toDTO(Profile entity);

    List<Profile> toEntity(List<ProfileDTO> dtoList);

    List<ProfileDTO> toDTO(List<Profile> entityList);

    ProfileLimDTO toDTOlim(Profile entity);

    List<ProfileLimDTO> toDTOlim(List<Profile> entityList);
}
