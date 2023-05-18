package com.security.api.serviceImpl;

import com.security.api.dto.ProfileDTO;
import com.security.api.dto.ResourceBranchDTO;
import com.security.api.entity.ResourceBranch;
import com.security.api.mapper.ProfileMapper;
import com.security.api.repository.*;
import com.security.api.service.ProfileService;
import com.security.api.util.GeneralResponse;
import com.security.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;
    private final ProfileMapper mapper;
    private final Utilities utilities;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceBranchRepository resourceBranchRepository;

    @Override
    @Transactional
    public GeneralResponse save(@NotNull ProfileDTO form) {
        log.info("ProfileServiceImpl.save");

        var business = this.businessRepository.findByPasiveIsFalseAndId(form.getIdBusiness());
        if (business == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        var idBusiness = this.utilities.getRealIdBusiness(form.getIdBusiness());
        if (idBusiness == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        try {
            var entity = this.mapper.toEntity(form);
            entity.setPasive(false);
            entity.setCode(this.generateSlug(form.getName()));
            entity.setCreatedByIp(this.utilities.getClientIp());

            var resourceBranches = entity.getResourceBranches();
            if (resourceBranches != null && !resourceBranches.isEmpty())
                for (ResourceBranch x : resourceBranches) {
                    var branch = this.branchRepository.findByPasiveIsFalseAndId(x.getIdBranch());
                    if (branch == null)
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    var resource = this.resourceRepository.findByIdAndPasiveIsFalse(x.getIdResource());
                    if (resource == null)
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    x.setBranch(branch);
                    x.setResource(resource);
                    x.setPasive(false);
                    x.setCreatedByIp(this.utilities.getClientIp());
                    x.setProfile(entity);
                }

            entity.setResourceBranches(resourceBranches);
            repository.save(entity);
            log.info("ProfileServiceImpl.save: success");
            return this.utilities.successResponse("Perfil guardado", null);
        } catch (Exception e) {
            log.error("ProfileServiceImpl.save: " + e.getMessage());
            e.printStackTrace();
            return this.utilities.exceptionResponse("Error al guardar el perfil", e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse update(ProfileDTO form, Integer id){
        log.info("ProfileServiceImpl.update");
        var idBusiness = this.utilities.getRealIdBusiness(form.getIdBusiness());
        if (idBusiness == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        var profile = this.repository.findByPasiveIsFalseAndId(id);
        if (profile == null)
            return this.utilities.errorResponse("No se encontró el perfil");

        try {
            profile.setName(form.getName());
            profile.setCode(this.generateSlug(form.getName()));
            profile.setUpdatedByIp(this.utilities.getClientIp());

            Set<ResourceBranch> list = new HashSet<>();

            for (ResourceBranchDTO x : form.getResourceBranches()){
                ResourceBranch resourceBranch = new ResourceBranch();
                if (x.getId() == null){
                    resourceBranch.setPasive(false);
                    resourceBranch.setCreatedByIp(this.utilities.getClientIp());

                    var branche = this.branchRepository.findByPasiveIsFalseAndId(x.getIdBranch());
                    if (branche == null)
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    var resource = this.resourceRepository.findByIdAndPasiveIsFalse(x.getIdResource());
                    if (resource == null)
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    resourceBranch.setIdBranch(x.getIdBranch());
                    resourceBranch.setIdResource(x.getIdResource());
                    resourceBranch.setBranch(branche);
                    resourceBranch.setResource(resource);
                    resourceBranch.setProfile(profile);

                    list.add(resourceBranch);
                }else {
                    resourceBranch = this.resourceBranchRepository.findByPasiveIsFalseAndId(x.getId());
                    if (resourceBranch == null)
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    if (!resourceBranch.getIdResource().equals(x.getIdResource())
                       || !resourceBranch.getIdBranch().equals(x.getIdBranch()))
                        return this.utilities.errorResponse("No se puede procesar la solicitud");

                    if (Boolean.TRUE.equals(x.getDeleted())) {
                        resourceBranch.setPasive(true);
                        resourceBranch.setUpdatedByIp(this.utilities.getClientIp());
                    }

                    list.add(resourceBranch);
                }
            }

            //profile.getResourceBranches().clear();
            profile.setResourceBranches(list);
            this.repository.save(profile);
            log.info("ProfileServiceImpl.update: success");
            return this.utilities.successResponse("Perfil actualizado", null);
        }catch (Exception e){
            log.error("ProfileServiceImpl.update: " + e.getMessage());
            e.printStackTrace();
            return this.utilities.exceptionResponse("Error al actualizar el perfil", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GeneralResponse getByBusiness(Integer idBusiness) {
        log.info("ProfileServiceImpl.getByBusiness");

        idBusiness = this.utilities.getRealIdBusiness(idBusiness);
        if (idBusiness == null)
            return this.utilities.errorResponse("No se encontraron perfiles");

        try {
            var list = this.repository.findAllByPasiveIsFalseAndIdBusiness(idBusiness);

            if (list.isEmpty())
                return this.utilities.errorResponse("No se encontraron perfiles");

            var dtoList = this.mapper.toDTOlim(list);
            log.info("ProfileServiceImpl.getByBusiness: success");
            return this.utilities.successResponse("Perfiles obtenidos", dtoList);
        } catch (Exception e) {
            log.error("ProfileServiceImpl.getByBusiness: " + e.getMessage());
            e.printStackTrace();
            return this.utilities.exceptionResponse("Error al obtener los perfiles", e);
        }
    }

    @Override
    public GeneralResponse getById(@NotNull Integer id, @NotNull Integer idBusiness) {
        log.info("ProfileServiceImpl.getById");

        idBusiness = this.utilities.getRealIdBusiness(idBusiness);
        if (idBusiness == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        var profile = this.repository.findByPasiveIsFalseAndId(id);
        if (profile == null)
            return this.utilities.errorResponse("No se encontró el perfil");

        if (profile.getIdBusiness() == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        if (!profile.getIdBusiness().equals(idBusiness))
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        try {
            var dto = this.mapper.toDTOlim(profile);
            log.info("ProfileServiceImpl.getById: success");
            return this.utilities.successResponse("Perfil obtenido", dto);
        } catch (Exception e) {
            log.error("ProfileServiceImpl.getById: " + e.getMessage());
            e.printStackTrace();
            return this.utilities.exceptionResponse("Error al obtener el perfil", e);
        }
    }

    @Override
    public GeneralResponse delete(Integer id, Integer idBusiness){
        log.info("ProfileServiceImpl.delete");

        idBusiness = this.utilities.getRealIdBusiness(idBusiness);
        if (idBusiness == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        var profile = this.repository.findByPasiveIsFalseAndId(id);
        if (profile == null)
            return this.utilities.errorResponse("No se encontró el perfil");

        if (profile.getIdBusiness() == null)
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        if (!profile.getIdBusiness().equals(idBusiness))
            return this.utilities.errorResponse("No se puede procesar la solicitud");

        try {
            profile.setPasive(true);
            profile.setUpdatedByIp(this.utilities.getClientIp());
            this.repository.save(profile);
            log.info("ProfileServiceImpl.delete: success");
            return this.utilities.successResponse("Perfil eliminado", null);
        } catch (Exception e) {
            log.error("ProfileServiceImpl.delete: " + e.getMessage());
            e.printStackTrace();
            return this.utilities.exceptionResponse("Error al eliminar el perfil", e);
        }
    }

    private String generateSlug(String cadena) {
        cadena = cadena.toLowerCase();
        cadena = cadena.replace(" ", "-");
        return cadena;
    }
}
