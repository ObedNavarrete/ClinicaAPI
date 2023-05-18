package com.security.api.serviceImpl;

import com.security.api.dto.ResourceDTO;
import com.security.api.mapper.ResourceMapper;
import com.security.api.repository.ResourceRepository;
import com.security.api.service.ResourceService;
import com.security.api.util.GeneralResponse;
import com.security.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository repository;
    private final ResourceMapper mapper;
    private final Utilities utilities;

    @Override
    public GeneralResponse save(ResourceDTO form) {
        log.info("save: {}", form);

        try {
            var entity = mapper.toEntity(form);
            entity.setCreatedByIp(utilities.getClientIp());
            repository.save(entity);
            log.info("save success: {}", entity);
            return this.utilities.successResponse("Recurso creado", null);
        } catch (Exception e) {
            log.error("save exception: {}", e.getMessage());
            return this.utilities.exceptionResponse("Ocurrió un error al crear el recurso", e);
        }
    }

    @Override
    public GeneralResponse getAll() {
        log.info("getAll");

        try {
            var entities = repository.findAllByPasiveIsFalse();

            if (entities.isEmpty()) {
                log.info("getAll empty");
                return this.utilities.errorResponse("No se encontraron recursos");
            }

            var dtos = mapper.toDTO(entities);
            log.info("getAll success: {}", dtos);
            return this.utilities.successResponse("Recursos encontrados", dtos);
        } catch (Exception e) {
            log.error("getAll exception: {}", e.getMessage());
            return this.utilities.exceptionResponse("Ocurrió un error al buscar los recursos", e);
        }
    }

    @Override
    public GeneralResponse update(Integer id, ResourceDTO form){
        log.info("update: {}", id);

        try {
            var entity = repository.findByIdAndPasiveIsFalse(id);

            if (entity == null) {
                log.info("update not found: {}", id);
                return this.utilities.errorResponse("No se encontró el recurso");
            }

            entity.setUpdatedByIp(utilities.getClientIp());
            entity.setUrl(form.getUrl());
            entity.setDescription(form.getDescription());
            repository.save(entity);
            log.info("update success: {}", entity);
            return this.utilities.successResponse("Recurso actualizado", null);
        } catch (Exception e) {
            log.error("update exception: {}", e.getMessage());
            return this.utilities.exceptionResponse("Ocurrió un error al actualizar el recurso", e);
        }
    }

    @Override
    public GeneralResponse delete(Integer id){
        log.info("delete: {}", id);

        try {
            var entity = repository.findByIdAndPasiveIsFalse(id);

            if (entity == null) {
                log.info("delete not found: {}", id);
                return this.utilities.errorResponse("No se encontró el recurso");
            }

            entity.setPasive(true);
            entity.setUpdatedByIp(utilities.getClientIp());
            repository.save(entity);
            log.info("delete success: {}", entity);
            return this.utilities.successResponse("Recurso eliminado", null);
        } catch (Exception e) {
            log.error("delete exception: {}", e.getMessage());
            return this.utilities.exceptionResponse("Ocurrió un error al eliminar el recurso", e);
        }
    }

}
