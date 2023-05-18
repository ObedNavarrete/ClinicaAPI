package com.security.api.repository;

import com.security.api.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByPasiveIsFalseAndId(Integer id);
    List<Business> findAllByPasiveIsFalse();
}
