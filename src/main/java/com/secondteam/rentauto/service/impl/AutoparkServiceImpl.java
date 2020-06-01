package com.secondteam.rentauto.service.impl;

import com.secondteam.rentauto.service.AutoparkService;
import com.secondteam.rentauto.domain.Autopark;
import com.secondteam.rentauto.repository.AutoparkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Autopark}.
 */
@Service
@Transactional
public class AutoparkServiceImpl implements AutoparkService {

    private final Logger log = LoggerFactory.getLogger(AutoparkServiceImpl.class);

    private final AutoparkRepository autoparkRepository;

    public AutoparkServiceImpl(AutoparkRepository autoparkRepository) {
        this.autoparkRepository = autoparkRepository;
    }

    /**
     * Save a autopark.
     *
     * @param autopark the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Autopark save(Autopark autopark) {
        log.debug("Request to save Autopark : {}", autopark);
        return autoparkRepository.save(autopark);
    }

    /**
     * Get all the autoparks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Autopark> findAll(Pageable pageable) {
        log.debug("Request to get all Autoparks");
        return autoparkRepository.findAll(pageable);
    }

    /**
     * Get one autopark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Autopark> findOne(Long id) {
        log.debug("Request to get Autopark : {}", id);
        return autoparkRepository.findById(id);
    }

    /**
     * Delete the autopark by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autopark : {}", id);
        autoparkRepository.deleteById(id);
    }
}
