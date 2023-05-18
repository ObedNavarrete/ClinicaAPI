package com.security.api.serviceImpl;

import com.security.api.dto.BusinessDTO;
import com.security.api.mapper.BusinessMapper;
import com.security.api.repository.BusinessRepository;
import com.security.api.service.BusinessService;
import com.security.api.util.GeneralResponse;
import com.security.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository repository;
    private final BusinessMapper mapper;
    private final Utilities utilities;

    @Override
    public GeneralResponse findAll() {
        log.info("BusinessServiceImpl.findAll");
        try {
            var businessList = repository.findAllByPasiveIsFalse();
            if (businessList.isEmpty()) return utilities.errorResponse("No se encontraron negocios");

            var res = mapper.toDTOs(businessList);
            return utilities.successResponse("Negocios encontrados", res);
        } catch (Exception e) {
            log.error("An error occurred mapping the businesses", e);
            return utilities.exceptionResponse("An error occurred mapping the businesses", e);
        }
    }

    @Override
    public GeneralResponse save(BusinessDTO request) {
        log.info("BusinessServiceImpl.save");

        try {
            var business = mapper.toEntity(request);
            business.setPasive(false);
            business.setCreatedByIp(utilities.getClientIp());
            repository.save(business);
            log.info("Business saved");
            return utilities.successResponse("Business saved", null);
        } catch (Exception e) {
            log.error("An error occurred saving the business", e);
            return utilities.exceptionResponse("An error occurred saving the business", e);
        }
    }

    @Override
    public GeneralResponse update(BusinessDTO request, Integer id) {
        log.info("BusinessServiceImpl.update");

        try {
            var business = repository.findById(id);
            if (business.isEmpty()) return utilities.errorResponse("Business not found");

            if (Boolean.FALSE.equals(this.utilities.havePermissionChangeResource(id)))
                return utilities.errorResponse("You not have permission for update this business");

            var businessToUpdate = business.get();
            businessToUpdate.setName(request.getName());
            businessToUpdate.setAddress(request.getAddress());
            businessToUpdate.setPhone(request.getPhone());
            businessToUpdate.setUpdatedByIp(utilities.getClientIp());
            repository.save(businessToUpdate);
            log.info("Business updated");
            return utilities.successResponse("Business updated", null);
        } catch (Exception e) {
            log.error("An error occurred updating the business", e);
            return utilities.exceptionResponse("An error occurred updating the business", e);
        }
    }

    @Override
    public GeneralResponse delete(Integer id) {
        log.info("BusinessServiceImpl.delete");

        // Deleted for users with profile MANAGER

        try {
            var business = repository.findById(id);
            if (business.isEmpty()) return utilities.errorResponse("Business not found");

            if (Boolean.FALSE.equals(this.utilities.havePermissionChangeResource(id)))
                return utilities.errorResponse("You not have permission for delete this business");

            var businessToDelete = business.get();
            businessToDelete.setPasive(true);
            businessToDelete.setUpdatedByIp(utilities.getClientIp());
            repository.save(businessToDelete);
            log.info("Business deleted");
            return utilities.successResponse("Business deleted", null);
        } catch (Exception e) {
            log.error("An error occurred deleting the business", e);
            return utilities.exceptionResponse("An error occurred deleting the business", e);
        }
    }

    @Override
    public GeneralResponse findById(Integer id) {
        log.info("BusinessServiceImpl.findById");

        try {
            id = utilities.getRealIdBusiness(id);
            if (id == null) return utilities.errorResponse("Business not found or not have permission");

            var business = repository.findByPasiveIsFalseAndId(id);
            if (business == null) return utilities.errorResponse("Business not found");

            var res = mapper.toDTO(business);
            log.info("Business found");
            return utilities.successResponse("Business found", res);
        } catch (Exception e) {
            log.error("An error occurred finding the business", e);
            return utilities.exceptionResponse("An error occurred finding the business", e);
        }
    }
}
