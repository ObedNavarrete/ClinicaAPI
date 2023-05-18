package com.security.api.service;

import com.security.api.util.GeneralResponse;

public interface CustomerService {
    GeneralResponse findByBusiness(Integer idBusiness);

    GeneralResponse findByUser(Integer idUser);
}
