package com.secondteam.rentauto.service;

import com.secondteam.rentauto.domain.Insuarence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Insuarence}.
 */
public interface InsuarenceService {

    /**
     * Save a insuarence.
     *
     * @param insuarence the entity to save.
     * @return the persisted entity.
     */
    Insuarence save(Insuarence insuarence);

    /**
     * Get all the insuarences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Insuarence> findAll(Pageable pageable);

    /**
     * Get the "id" insuarence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Insuarence> findOne(Long id);

    /**
     * Delete the "id" insuarence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
