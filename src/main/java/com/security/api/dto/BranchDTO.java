package com.security.api.dto;

import lombok.Data;

@Data
public class BranchDTO {
    private Integer id;
    private Integer idBusiness;
    private BusinessDTO business;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String city;
}
