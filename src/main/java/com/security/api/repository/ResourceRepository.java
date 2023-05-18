package com.security.api.repository;

import com.security.api.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    List<Resource> findAllByPasiveIsFalse();
    Resource findByIdAndPasiveIsFalse(Integer id);
}
