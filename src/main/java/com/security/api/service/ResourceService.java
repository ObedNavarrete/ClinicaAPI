package com.security.api.service;

import com.security.api.dto.ResourceDTO;
import com.security.api.util.GeneralResponse;

public interface ResourceService {
    GeneralResponse save(ResourceDTO form);

    GeneralResponse getAll();

    GeneralResponse update(Integer id, ResourceDTO form);

    GeneralResponse delete(Integer id);
}
