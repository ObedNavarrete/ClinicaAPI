package com.security.api.repository;

import com.security.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Profile> findAllByPasiveIsFalse();
    Profile findByPasiveIsFalseAndId(Integer id);
    @Query(value="select * from configuration.profile where id_business = ?1 or id_business is null and pasive = false", nativeQuery = true)
    List<Profile> findAllByPasiveIsFalseAndIdBusiness(Integer idBusiness);
}
