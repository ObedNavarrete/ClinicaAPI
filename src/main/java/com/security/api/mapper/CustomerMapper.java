package com.security.api.mapper;

import com.security.api.dto.CustomerDTO;
import com.security.api.entity.Customer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDTO dto);
    CustomerDTO toDTO(Customer entity);
    List<CustomerDTO> toDTOs(List<Customer> entities);
    List<Customer> toEntities(List<CustomerDTO> dtos);
}
