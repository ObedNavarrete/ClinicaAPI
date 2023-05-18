package com.security.api.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer id;
    private UserLimDTO user;
    private BusinessDTO business;
}
