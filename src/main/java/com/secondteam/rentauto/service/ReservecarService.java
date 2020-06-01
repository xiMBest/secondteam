package com.secondteam.rentauto.service;

import com.secondteam.rentauto.domain.Reservecar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Reservecar}.
 */
public interface ReservecarService {

    /**
     * Save a reservecar.
     *
     * @param reservecar the entity to save.
     * @return the persisted entity.
     */
    Reservecar save(Reservecar reservecar);

    /**
     * Get all the reservecars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reservecar> findAll(Pageable pageable);

    /**
     * Get the "id" reservecar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reservecar> findOne(Long id);

    /**
     * Delete the "id" reservecar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
