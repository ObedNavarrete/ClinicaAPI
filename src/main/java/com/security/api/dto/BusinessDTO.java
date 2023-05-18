package com.security.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BusinessDTO {
    private Integer id;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Address is required")
    private String address;
    @NotNull(message = "Phone is required")
    private String phone;
}
