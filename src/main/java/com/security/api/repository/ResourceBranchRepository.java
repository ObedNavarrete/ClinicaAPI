package com.security.api.repository;

import com.security.api.entity.Branch;
import com.security.api.entity.Resource;
import com.security.api.entity.ResourceBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceBranchRepository extends JpaRepository<ResourceBranch, Integer> {
    ResourceBranch findByPasiveIsFalseAndIdBranchAndIdResource(Integer idBranch, Integer idResource);
    ResourceBranch findByPasiveIsFalseAndId(Integer id);
    
}
