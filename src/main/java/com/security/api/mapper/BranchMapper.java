package com.security.api.mapper;

import com.security.api.dto.BranchDTO;
import com.security.api.entity.Branch;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface BranchMapper {
    Branch toEntity(BranchDTO dto);
    BranchDTO toDTO(Branch entity);
    List<Branch> toEntity(List<BranchDTO> dtoList);
    List<BranchDTO> toDTO(List<Branch> entityList);
}
