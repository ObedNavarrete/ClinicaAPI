package com.security.api.serviceImpl;

import com.security.api.dto.BranchDTO;
import com.security.api.mapper.BranchMapper;
import com.security.api.repository.BranchRepository;
import com.security.api.service.BranchService;
import com.security.api.util.GeneralResponse;
import com.security.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository repository;
    private final BranchMapper mapper;
    private final Utilities utilities;

    @Override
    public GeneralResponse findByBusiness(Integer idBusiness){
        log.info("BranchServiceImpl.findByBusiness");

        Integer businessId = utilities.getRealIdBusiness(idBusiness);
        if (businessId == null) return utilities.errorResponse("You not have permission for view data for this business");

        var branchList = repository.findAllByPasiveIsFalseAndIdBusiness(businessId);
        if (branchList.isEmpty()) return utilities.errorResponse("Branches not found");

        try {
            var res = mapper.toDTO(branchList);
            return utilities.successResponse("Branches found", res);
        } catch (Exception e) {
            log.error("An error occurred mapping the branches", e);
            return utilities.exceptionResponse("An error occurred mapping the branches", e);
        }
    }

    @Override
    public GeneralResponse save(BranchDTO form, Integer idBusiness){
        log.info("BranchServiceImpl.save");

        Integer businessId = utilities.getRealIdBusiness(idBusiness);
        if (businessId == null) return utilities.errorResponse("You not have permission for view data for this business");

        try {
            var branch = mapper.toEntity(form);
            branch.setIdBusiness(businessId);
            branch.setPasive(false);
            branch.setCreatedByIp(utilities.getClientIp());
            repository.save(branch);
            log.info("Branch saved");
            return utilities.successResponse("Branch saved", mapper.toDTO(branch));
        } catch (Exception e) {
            log.error("An error occurred saving the branch", e);
            return utilities.exceptionResponse("An error occurred saving the branch", e);
        }
    }

    @Override
    public GeneralResponse update(BranchDTO form, Integer id){
        log.info("BranchServiceImpl.update");

        if (Boolean.TRUE.equals(this.utilities.havePermissionChangeResource(form.getIdBusiness())))
            return utilities.errorResponse("You not have permission for update this branch");

        var branch = repository.findByPasiveIsFalseAndId(form.getId());
        if (branch == null) return utilities.errorResponse("Branch not found");

        try {
            branch.setName(form.getName());
            branch.setAddress(form.getAddress());
            branch.setCity(form.getCity());
            branch.setPhone(form.getPhone());
            branch.setEmail(form.getEmail());
            branch.setUpdatedByIp(utilities.getClientIp());
            repository.save(branch);
            log.info("Branch updated");
            return utilities.successResponse("Branch updated", null);
        } catch (Exception e) {
            log.error("An error occurred updating the branch", e);
            return utilities.exceptionResponse("An error occurred updating the branch", e);
        }
    }

    @Override
    public GeneralResponse delete(Integer id){
        log.info("BranchServiceImpl.delete");

        var branch = repository.findByPasiveIsFalseAndId(id);
        if (branch == null) return utilities.errorResponse("Branch not found");

        if (Boolean.TRUE.equals(this.utilities.havePermissionChangeResource(branch.getIdBusiness())))
            return utilities.errorResponse("You not have permission for delete this branch");

        try {
            branch.setPasive(true);
            branch.setUpdatedByIp(utilities.getClientIp());
            repository.save(branch);
            log.info("Branch deleted");
            return utilities.successResponse("Branch deleted", null);
        } catch (Exception e) {
            log.error("An error occurred deleting the branch", e);
            return utilities.exceptionResponse("An error occurred deleting the branch", e);
        }
    }
}
