package com.secondteam.rentauto.service;

import com.secondteam.rentauto.domain.Autopark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Autopark}.
 */
public interface AutoparkService {

    /**
     * Save a autopark.
     *
     * @param autopark the entity to save.
     * @return the persisted entity.
     */
    Autopark save(Autopark autopark);

    /**
     * Get all the autoparks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Autopark> findAll(Pageable pageable);

    /**
     * Get the "id" autopark.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Autopark> findOne(Long id);

    /**
     * Delete the "id" autopark.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
