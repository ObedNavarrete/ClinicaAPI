package com.security.api.repository;

import com.security.api.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Branch findByPasiveIsFalseAndId(Integer id);
    List<Branch> findAllByPasiveIsFalseAndIdBusiness(Integer idBusiness);
}
