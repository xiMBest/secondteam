package com.secondteam.rentauto.repository;

import com.secondteam.rentauto.domain.Insuarence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Insuarence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuarenceRepository extends JpaRepository<Insuarence, Long>, JpaSpecificationExecutor<Insuarence> {
}
