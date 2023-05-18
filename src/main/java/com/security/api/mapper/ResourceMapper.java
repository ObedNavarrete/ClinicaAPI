package com.security.api.mapper;

import com.security.api.dto.ResourceDTO;
import com.security.api.entity.Resource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource toEntity(ResourceDTO dto);
    ResourceDTO toDTO(Resource entity);
    List<Resource> toEntity(List<ResourceDTO> dtoList);
    List<ResourceDTO> toDTO(List<Resource> entityList);
}
