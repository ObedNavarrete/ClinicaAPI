package com.security.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResourceBranchDTO {
    private Integer id;
    @NotNull(message = "El recurso es requerido")
    private Integer idResource;
    private ResourceDTO resource;
    @NotNull(message = "La sucursal es requerida")
    private Integer idBranch;
    private BranchDTO branch;

    private Boolean deleted;
}
