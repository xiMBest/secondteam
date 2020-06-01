package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.domain.Insuarence;
import com.secondteam.rentauto.service.InsuarenceService;
import com.secondteam.rentauto.web.rest.errors.BadRequestAlertException;
import com.secondteam.rentauto.service.dto.InsuarenceCriteria;
import com.secondteam.rentauto.service.InsuarenceQueryService;

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
 * REST controller for managing {@link com.secondteam.rentauto.domain.Insuarence}.
 */
@RestController
@RequestMapping("/api")
public class InsuarenceResource {

    private final Logger log = LoggerFactory.getLogger(InsuarenceResource.class);

    private static final String ENTITY_NAME = "insuarence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsuarenceService insuarenceService;

    private final InsuarenceQueryService insuarenceQueryService;

    public InsuarenceResource(InsuarenceService insuarenceService, InsuarenceQueryService insuarenceQueryService) {
        this.insuarenceService = insuarenceService;
        this.insuarenceQueryService = insuarenceQueryService;
    }

    /**
     * {@code POST  /insuarences} : Create a new insuarence.
     *
     * @param insuarence the insuarence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insuarence, or with status {@code 400 (Bad Request)} if the insuarence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insuarences")
    public ResponseEntity<Insuarence> createInsuarence(@RequestBody Insuarence insuarence) throws URISyntaxException {
        log.debug("REST request to save Insuarence : {}", insuarence);
        if (insuarence.getId() != null) {
            throw new BadRequestAlertException("A new insuarence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Insuarence result = insuarenceService.save(insuarence);
        return ResponseEntity.created(new URI("/api/insuarences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insuarences} : Updates an existing insuarence.
     *
     * @param insuarence the insuarence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insuarence,
     * or with status {@code 400 (Bad Request)} if the insuarence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insuarence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insuarences")
    public ResponseEntity<Insuarence> updateInsuarence(@RequestBody Insuarence insuarence) throws URISyntaxException {
        log.debug("REST request to update Insuarence : {}", insuarence);
        if (insuarence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Insuarence result = insuarenceService.save(insuarence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insuarence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insuarences} : get all the insuarences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insuarences in body.
     */
    @GetMapping("/insuarences")
    public ResponseEntity<List<Insuarence>> getAllInsuarences(InsuarenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Insuarences by criteria: {}", criteria);
        Page<Insuarence> page = insuarenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insuarences/count} : count all the insuarences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/insuarences/count")
    public ResponseEntity<Long> countInsuarences(InsuarenceCriteria criteria) {
        log.debug("REST request to count Insuarences by criteria: {}", criteria);
        return ResponseEntity.ok().body(insuarenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /insuarences/:id} : get the "id" insuarence.
     *
     * @param id the id of the insuarence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insuarence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insuarences/{id}")
    public ResponseEntity<Insuarence> getInsuarence(@PathVariable Long id) {
        log.debug("REST request to get Insuarence : {}", id);
        Optional<Insuarence> insuarence = insuarenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insuarence);
    }

    /**
     * {@code DELETE  /insuarences/:id} : delete the "id" insuarence.
     *
     * @param id the id of the insuarence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insuarences/{id}")
    public ResponseEntity<Void> deleteInsuarence(@PathVariable Long id) {
        log.debug("REST request to delete Insuarence : {}", id);
        insuarenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
