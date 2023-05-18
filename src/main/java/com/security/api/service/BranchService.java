package com.security.api.service;

import com.security.api.dto.BranchDTO;
import com.security.api.util.GeneralResponse;

public interface BranchService {
    GeneralResponse findByBusiness(Integer idBusiness);

    GeneralResponse save(BranchDTO form, Integer idBusiness);

    GeneralResponse update(BranchDTO form, Integer id);

    GeneralResponse delete(Integer id);
}
