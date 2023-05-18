package com.security.api.dto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data @Builder
public class ProfileDTO {
    private Integer id;
    @NotNull(message = "El nombre es requerido")
    private String name;
    private String code;
    @NotNull(message = "La empresa es requerida")
    private Integer idBusiness;
    private BusinessDTO business;

    @Valid
    private Set<ResourceBranchDTO> resourceBranches;
}
