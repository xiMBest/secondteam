package com.secondteam.rentauto.service.impl;

import com.secondteam.rentauto.service.InsuarenceService;
import com.secondteam.rentauto.domain.Insuarence;
import com.secondteam.rentauto.repository.InsuarenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Insuarence}.
 */
@Service
@Transactional
public class InsuarenceServiceImpl implements InsuarenceService {

    private final Logger log = LoggerFactory.getLogger(InsuarenceServiceImpl.class);

    private final InsuarenceRepository insuarenceRepository;

    public InsuarenceServiceImpl(InsuarenceRepository insuarenceRepository) {
        this.insuarenceRepository = insuarenceRepository;
    }

    /**
     * Save a insuarence.
     *
     * @param insuarence the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Insuarence save(Insuarence insuarence) {
        log.debug("Request to save Insuarence : {}", insuarence);
        return insuarenceRepository.save(insuarence);
    }

    /**
     * Get all the insuarences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Insuarence> findAll(Pageable pageable) {
        log.debug("Request to get all Insuarences");
        return insuarenceRepository.findAll(pageable);
    }

    /**
     * Get one insuarence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Insuarence> findOne(Long id) {
        log.debug("Request to get Insuarence : {}", id);
        return insuarenceRepository.findById(id);
    }

    /**
     * Delete the insuarence by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insuarence : {}", id);
        insuarenceRepository.deleteById(id);
    }
}
