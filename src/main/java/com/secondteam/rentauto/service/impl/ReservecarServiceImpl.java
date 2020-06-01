package com.secondteam.rentauto.service.impl;

import com.secondteam.rentauto.service.ReservecarService;
import com.secondteam.rentauto.domain.Reservecar;
import com.secondteam.rentauto.repository.ReservecarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reservecar}.
 */
@Service
@Transactional
public class ReservecarServiceImpl implements ReservecarService {

    private final Logger log = LoggerFactory.getLogger(ReservecarServiceImpl.class);

    private final ReservecarRepository reservecarRepository;

    public ReservecarServiceImpl(ReservecarRepository reservecarRepository) {
        this.reservecarRepository = reservecarRepository;
    }

    /**
     * Save a reservecar.
     *
     * @param reservecar the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Reservecar save(Reservecar reservecar) {
        log.debug("Request to save Reservecar : {}", reservecar);
        return reservecarRepository.save(reservecar);
    }

    /**
     * Get all the reservecars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reservecar> findAll(Pageable pageable) {
        log.debug("Request to get all Reservecars");
        return reservecarRepository.findAll(pageable);
    }

    /**
     * Get one reservecar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reservecar> findOne(Long id) {
        log.debug("Request to get Reservecar : {}", id);
        return reservecarRepository.findById(id);
    }

    /**
     * Delete the reservecar by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reservecar : {}", id);
        reservecarRepository.deleteById(id);
    }
}
