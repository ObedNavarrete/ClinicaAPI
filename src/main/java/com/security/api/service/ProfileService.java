package com.security.api.service;

import com.security.api.dto.ProfileDTO;
import com.security.api.util.GeneralResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

public interface ProfileService {
    GeneralResponse save(ProfileDTO form);

    @Transactional
    GeneralResponse update(ProfileDTO form, Integer id);

    GeneralResponse getByBusiness(Integer idBusiness);

    GeneralResponse getById(@NotNull Integer id, @NotNull Integer idBusiness);

    GeneralResponse delete(Integer id, Integer idBusiness);
}
