package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.domain.Fixcar;
import com.secondteam.rentauto.service.FixcarService;
import com.secondteam.rentauto.web.rest.errors.BadRequestAlertException;
import com.secondteam.rentauto.service.dto.FixcarCriteria;
import com.secondteam.rentauto.service.FixcarQueryService;

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
 * REST controller for managing {@link com.secondteam.rentauto.domain.Fixcar}.
 */
@RestController
@RequestMapping("/api")
public class FixcarResource {

    private final Logger log = LoggerFactory.getLogger(FixcarResource.class);

    private static final String ENTITY_NAME = "fixcar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixcarService fixcarService;

    private final FixcarQueryService fixcarQueryService;

    public FixcarResource(FixcarService fixcarService, FixcarQueryService fixcarQueryService) {
        this.fixcarService = fixcarService;
        this.fixcarQueryService = fixcarQueryService;
    }

    /**
     * {@code POST  /fixcars} : Create a new fixcar.
     *
     * @param fixcar the fixcar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixcar, or with status {@code 400 (Bad Request)} if the fixcar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixcars")
    public ResponseEntity<Fixcar> createFixcar(@RequestBody Fixcar fixcar) throws URISyntaxException {
        log.debug("REST request to save Fixcar : {}", fixcar);
        if (fixcar.getId() != null) {
            throw new BadRequestAlertException("A new fixcar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fixcar result = fixcarService.save(fixcar);
        return ResponseEntity.created(new URI("/api/fixcars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixcars} : Updates an existing fixcar.
     *
     * @param fixcar the fixcar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixcar,
     * or with status {@code 400 (Bad Request)} if the fixcar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixcar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixcars")
    public ResponseEntity<Fixcar> updateFixcar(@RequestBody Fixcar fixcar) throws URISyntaxException {
        log.debug("REST request to update Fixcar : {}", fixcar);
        if (fixcar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fixcar result = fixcarService.save(fixcar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fixcar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixcars} : get all the fixcars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixcars in body.
     */
    @GetMapping("/fixcars")
    public ResponseEntity<List<Fixcar>> getAllFixcars(FixcarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fixcars by criteria: {}", criteria);
        Page<Fixcar> page = fixcarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fixcars/count} : count all the fixcars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fixcars/count")
    public ResponseEntity<Long> countFixcars(FixcarCriteria criteria) {
        log.debug("REST request to count Fixcars by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixcarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixcars/:id} : get the "id" fixcar.
     *
     * @param id the id of the fixcar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixcar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixcars/{id}")
    public ResponseEntity<Fixcar> getFixcar(@PathVariable Long id) {
        log.debug("REST request to get Fixcar : {}", id);
        Optional<Fixcar> fixcar = fixcarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixcar);
    }

    /**
     * {@code DELETE  /fixcars/:id} : delete the "id" fixcar.
     *
     * @param id the id of the fixcar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixcars/{id}")
    public ResponseEntity<Void> deleteFixcar(@PathVariable Long id) {
        log.debug("REST request to delete Fixcar : {}", id);
        fixcarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
