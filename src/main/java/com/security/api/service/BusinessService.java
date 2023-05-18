package com.security.api.service;

import com.security.api.dto.BusinessDTO;
import com.security.api.util.GeneralResponse;

public interface BusinessService {
    GeneralResponse findAll();

    GeneralResponse save(BusinessDTO request);

    GeneralResponse update(BusinessDTO request, Integer id);

    GeneralResponse delete(Integer id);

    GeneralResponse findById(Integer id);
}
