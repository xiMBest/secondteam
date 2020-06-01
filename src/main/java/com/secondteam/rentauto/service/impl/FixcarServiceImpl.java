package com.secondteam.rentauto.service.impl;

import com.secondteam.rentauto.service.FixcarService;
import com.secondteam.rentauto.domain.Fixcar;
import com.secondteam.rentauto.repository.FixcarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fixcar}.
 */
@Service
@Transactional
public class FixcarServiceImpl implements FixcarService {

    private final Logger log = LoggerFactory.getLogger(FixcarServiceImpl.class);

    private final FixcarRepository fixcarRepository;

    public FixcarServiceImpl(FixcarRepository fixcarRepository) {
        this.fixcarRepository = fixcarRepository;
    }

    /**
     * Save a fixcar.
     *
     * @param fixcar the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Fixcar save(Fixcar fixcar) {
        log.debug("Request to save Fixcar : {}", fixcar);
        return fixcarRepository.save(fixcar);
    }

    /**
     * Get all the fixcars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fixcar> findAll(Pageable pageable) {
        log.debug("Request to get all Fixcars");
        return fixcarRepository.findAll(pageable);
    }

    /**
     * Get all the fixcars with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Fixcar> findAllWithEagerRelationships(Pageable pageable) {
        return fixcarRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one fixcar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fixcar> findOne(Long id) {
        log.debug("Request to get Fixcar : {}", id);
        return fixcarRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the fixcar by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fixcar : {}", id);
        fixcarRepository.deleteById(id);
    }
}
