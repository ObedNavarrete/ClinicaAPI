package com.security.api.dto;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class ProfileLimDTO {
    private Integer id;
    private String name;
    private String code;
    private BusinessDTO business;
    private Set<Map<String,Object>> detailResourceBranches;
}
