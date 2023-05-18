package com.security.api.repository;

import com.security.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByPasiveIsFalseAndId(Integer id);
    List<Customer> findAllByPasiveIsFalseAndIdBusiness(Integer idBusiness);
    Customer findByPasiveIsFalseAndIdUser(Integer idUser);
}


// controller -> service -> repository
// repository -> service -> controller