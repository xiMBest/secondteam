package com.secondteam.rentauto.repository;

import com.secondteam.rentauto.domain.Reservecar;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reservecar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservecarRepository extends JpaRepository<Reservecar, Long>, JpaSpecificationExecutor<Reservecar> {
}
