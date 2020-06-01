package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.domain.Penalty;
import com.secondteam.rentauto.service.PenaltyService;
import com.secondteam.rentauto.web.rest.errors.BadRequestAlertException;
import com.secondteam.rentauto.service.dto.PenaltyCriteria;
import com.secondteam.rentauto.service.PenaltyQueryService;

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
 * REST controller for managing {@link com.secondteam.rentauto.domain.Penalty}.
 */
@RestController
@RequestMapping("/api")
public class PenaltyResource {

    private final Logger log = LoggerFactory.getLogger(PenaltyResource.class);

    private static final String ENTITY_NAME = "penalty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PenaltyService penaltyService;

    private final PenaltyQueryService penaltyQueryService;

    public PenaltyResource(PenaltyService penaltyService, PenaltyQueryService penaltyQueryService) {
        this.penaltyService = penaltyService;
        this.penaltyQueryService = penaltyQueryService;
    }

    /**
     * {@code POST  /penalties} : Create a new penalty.
     *
     * @param penalty the penalty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new penalty, or with status {@code 400 (Bad Request)} if the penalty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/penalties")
    public ResponseEntity<Penalty> createPenalty(@RequestBody Penalty penalty) throws URISyntaxException {
        log.debug("REST request to save Penalty : {}", penalty);
        if (penalty.getId() != null) {
            throw new BadRequestAlertException("A new penalty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Penalty result = penaltyService.save(penalty);
        return ResponseEntity.created(new URI("/api/penalties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /penalties} : Updates an existing penalty.
     *
     * @param penalty the penalty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated penalty,
     * or with status {@code 400 (Bad Request)} if the penalty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the penalty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/penalties")
    public ResponseEntity<Penalty> updatePenalty(@RequestBody Penalty penalty) throws URISyntaxException {
        log.debug("REST request to update Penalty : {}", penalty);
        if (penalty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Penalty result = penaltyService.save(penalty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, penalty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /penalties} : get all the penalties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of penalties in body.
     */
    @GetMapping("/penalties")
    public ResponseEntity<List<Penalty>> getAllPenalties(PenaltyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Penalties by criteria: {}", criteria);
        Page<Penalty> page = penaltyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /penalties/count} : count all the penalties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/penalties/count")
    public ResponseEntity<Long> countPenalties(PenaltyCriteria criteria) {
        log.debug("REST request to count Penalties by criteria: {}", criteria);
        return ResponseEntity.ok().body(penaltyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /penalties/:id} : get the "id" penalty.
     *
     * @param id the id of the penalty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the penalty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/penalties/{id}")
    public ResponseEntity<Penalty> getPenalty(@PathVariable Long id) {
        log.debug("REST request to get Penalty : {}", id);
        Optional<Penalty> penalty = penaltyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(penalty);
    }

    /**
     * {@code DELETE  /penalties/:id} : delete the "id" penalty.
     *
     * @param id the id of the penalty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/penalties/{id}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Long id) {
        log.debug("REST request to delete Penalty : {}", id);
        penaltyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
