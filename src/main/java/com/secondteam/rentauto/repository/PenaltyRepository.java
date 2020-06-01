package com.secondteam.rentauto.repository;

import com.secondteam.rentauto.domain.Penalty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Penalty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long>, JpaSpecificationExecutor<Penalty> {
}
