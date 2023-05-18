package com.security.api.serviceImpl;

import com.security.api.mapper.CustomerMapper;
import com.security.api.repository.CustomerRepository;
import com.security.api.service.CustomerService;
import com.security.api.util.GeneralResponse;
import com.security.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final Utilities utilities;

    @Override
    public GeneralResponse findByBusiness(Integer idBusiness){
        log.info("CustomerServiceImpl.findByBusiness");

        Integer businessId = utilities.getRealIdBusiness(idBusiness);
        if (businessId == null) return utilities.errorResponse("You not have permission for view data for this business");

        var customerList = repository.findAllByPasiveIsFalseAndIdBusiness(businessId);
        if (customerList.isEmpty()) return utilities.errorResponse("Customers not found");

        try {
            var res = mapper.toDTOs(customerList);
            return utilities.successResponse("Customers found", res);
        } catch (Exception e) {
            log.error("An error occurred mapping the customers", e);
            return utilities.exceptionResponse("An error occurred mapping the customers", e);
        }
    }

    @Override
    public GeneralResponse findByUser(Integer idUser){
        log.info("CustomerServiceImpl.findByUser");

        var customer = repository.findByPasiveIsFalseAndIdUser(idUser);
        if (customer == null) return utilities.errorResponse("Customer not found");

        try {
            var res = mapper.toDTO(customer);
            return utilities.successResponse("Customer found", res);
        } catch (Exception e) {
            log.error("An error occurred mapping the customer", e);
            return utilities.exceptionResponse("An error occurred mapping the customer", e);
        }
    }
}
