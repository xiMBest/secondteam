package com.secondteam.rentauto.service.impl;

import com.secondteam.rentauto.service.PenaltyService;
import com.secondteam.rentauto.domain.Penalty;
import com.secondteam.rentauto.repository.PenaltyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Penalty}.
 */
@Service
@Transactional
public class PenaltyServiceImpl implements PenaltyService {

    private final Logger log = LoggerFactory.getLogger(PenaltyServiceImpl.class);

    private final PenaltyRepository penaltyRepository;

    public PenaltyServiceImpl(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    /**
     * Save a penalty.
     *
     * @param penalty the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Penalty save(Penalty penalty) {
        log.debug("Request to save Penalty : {}", penalty);
        return penaltyRepository.save(penalty);
    }

    /**
     * Get all the penalties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Penalty> findAll(Pageable pageable) {
        log.debug("Request to get all Penalties");
        return penaltyRepository.findAll(pageable);
    }

    /**
     * Get one penalty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Penalty> findOne(Long id) {
        log.debug("Request to get Penalty : {}", id);
        return penaltyRepository.findById(id);
    }

    /**
     * Delete the penalty by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Penalty : {}", id);
        penaltyRepository.deleteById(id);
    }
}
