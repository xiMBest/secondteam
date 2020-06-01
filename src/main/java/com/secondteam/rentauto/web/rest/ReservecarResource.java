package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.domain.Reservecar;
import com.secondteam.rentauto.service.ReservecarService;
import com.secondteam.rentauto.web.rest.errors.BadRequestAlertException;
import com.secondteam.rentauto.service.dto.ReservecarCriteria;
import com.secondteam.rentauto.service.ReservecarQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.secondteam.rentauto.domain.Reservecar}.
 */
@RestController
@RequestMapping("/api")
public class ReservecarResource {

    private final Logger log = LoggerFactory.getLogger(ReservecarResource.class);

    private static final String ENTITY_NAME = "reservecar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservecarService reservecarService;

    private final ReservecarQueryService reservecarQueryService;

    public ReservecarResource(ReservecarService reservecarService, ReservecarQueryService reservecarQueryService) {
        this.reservecarService = reservecarService;
        this.reservecarQueryService = reservecarQueryService;
    }

    /**
     * {@code POST  /reservecars} : Create a new reservecar.
     *
     * @param reservecar the reservecar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservecar, or with status {@code 400 (Bad Request)} if the reservecar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservecars")
    public ResponseEntity<Reservecar> createReservecar(@RequestBody Reservecar reservecar) throws URISyntaxException {
        log.debug("REST request to save Reservecar : {}", reservecar);
        if (reservecar.getId() != null) {
            throw new BadRequestAlertException("A new reservecar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reservecar result = reservecarService.save(reservecar);
        return ResponseEntity.created(new URI("/api/reservecars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservecars} : Updates an existing reservecar.
     *
     * @param reservecar the reservecar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservecar,
     * or with status {@code 400 (Bad Request)} if the reservecar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservecar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservecars")
    public ResponseEntity<Reservecar> updateReservecar(@RequestBody Reservecar reservecar) throws URISyntaxException {
        log.debug("REST request to update Reservecar : {}", reservecar);
        if (reservecar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reservecar result = reservecarService.save(reservecar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservecar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reservecars} : get all the reservecars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservecars in body.
     */
    @GetMapping("/reservecars")
    public ResponseEntity<List<Reservecar>> getAllReservecars(ReservecarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reservecars by criteria: {}", criteria);
        Page<Reservecar> page = reservecarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reservecars/count} : count all the reservecars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reservecars/count")
    public ResponseEntity<Long> countReservecars(ReservecarCriteria criteria) {
        log.debug("REST request to count Reservecars by criteria: {}", criteria);
        return ResponseEntity.ok().body(reservecarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reservecars/:id} : get the "id" reservecar.
     *
     * @param id the id of the reservecar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservecar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservecars/{id}")
    public ResponseEntity<Reservecar> getReservecar(@PathVariable Long id) {
        log.debug("REST request to get Reservecar : {}", id);
        Optional<Reservecar> reservecar = reservecarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservecar);
    }

    /**
     * {@code DELETE  /reservecars/:id} : delete the "id" reservecar.
     *
     * @param id the id of the reservecar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservecars/{id}")
    public ResponseEntity<Void> deleteReservecar(@PathVariable Long id) {
        log.debug("REST request to delete Reservecar : {}", id);
        reservecarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
