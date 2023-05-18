package com.security.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResourceDTO {
    private Integer id;
    @NotNull(message = "La url es requerida")
    private String url;
    private String description;
}
