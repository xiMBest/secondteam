package com.secondteam.rentauto.repository;

import com.secondteam.rentauto.domain.Fixcar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Fixcar entity.
 */
@Repository
public interface FixcarRepository extends JpaRepository<Fixcar, Long>, JpaSpecificationExecutor<Fixcar> {

    @Query(value = "select distinct fixcar from Fixcar fixcar left join fetch fixcar.autoparks",
        countQuery = "select count(distinct fixcar) from Fixcar fixcar")
    Page<Fixcar> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct fixcar from Fixcar fixcar left join fetch fixcar.autoparks")
    List<Fixcar> findAllWithEagerRelationships();

    @Query("select fixcar from Fixcar fixcar left join fetch fixcar.autoparks where fixcar.id =:id")
    Optional<Fixcar> findOneWithEagerRelationships(@Param("id") Long id);
}
