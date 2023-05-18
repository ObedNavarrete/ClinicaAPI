package com.security.api.mapper;

import com.security.api.dto.BusinessDTO;
import com.security.api.entity.Business;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface BusinessMapper {
    Business toEntity(BusinessDTO dto);
    BusinessDTO toDTO(Business entity);
    List<BusinessDTO> toDTOs(List<Business> entities);
    List<Business> toEntities(List<BusinessDTO> dtos);
}
