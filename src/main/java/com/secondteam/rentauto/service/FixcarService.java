package com.secondteam.rentauto.service;

import com.secondteam.rentauto.domain.Fixcar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Fixcar}.
 */
public interface FixcarService {

    /**
     * Save a fixcar.
     *
     * @param fixcar the entity to save.
     * @return the persisted entity.
     */
    Fixcar save(Fixcar fixcar);

    /**
     * Get all the fixcars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fixcar> findAll(Pageable pageable);

    /**
     * Get all the fixcars with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Fixcar> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fixcar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fixcar> findOne(Long id);

    /**
     * Delete the "id" fixcar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
