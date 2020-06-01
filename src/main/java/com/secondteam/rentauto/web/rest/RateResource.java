package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.domain.Rate;
import com.secondteam.rentauto.service.RateService;
import com.secondteam.rentauto.web.rest.errors.BadRequestAlertException;
import com.secondteam.rentauto.service.dto.RateCriteria;
import com.secondteam.rentauto.service.RateQueryService;

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
 * REST controller for managing {@link com.secondteam.rentauto.domain.Rate}.
 */
@RestController
@RequestMapping("/api")
public class RateResource {

    private final Logger log = LoggerFactory.getLogger(RateResource.class);

    private static final String ENTITY_NAME = "rate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RateService rateService;

    private final RateQueryService rateQueryService;

    public RateResource(RateService rateService, RateQueryService rateQueryService) {
        this.rateService = rateService;
        this.rateQueryService = rateQueryService;
    }

    /**
     * {@code POST  /rates} : Create a new rate.
     *
     * @param rate the rate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rate, or with status {@code 400 (Bad Request)} if the rate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rates")
    public ResponseEntity<Rate> createRate(@RequestBody Rate rate) throws URISyntaxException {
        log.debug("REST request to save Rate : {}", rate);
        if (rate.getId() != null) {
            throw new BadRequestAlertException("A new rate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rate result = rateService.save(rate);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rates} : Updates an existing rate.
     *
     * @param rate the rate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rate,
     * or with status {@code 400 (Bad Request)} if the rate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rates")
    public ResponseEntity<Rate> updateRate(@RequestBody Rate rate) throws URISyntaxException {
        log.debug("REST request to update Rate : {}", rate);
        if (rate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rate result = rateService.save(rate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rates} : get all the rates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rates in body.
     */
    @GetMapping("/rates")
    public ResponseEntity<List<Rate>> getAllRates(RateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rates by criteria: {}", criteria);
        Page<Rate> page = rateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rates/count} : count all the rates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rates/count")
    public ResponseEntity<Long> countRates(RateCriteria criteria) {
        log.debug("REST request to count Rates by criteria: {}", criteria);
        return ResponseEntity.ok().body(rateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rates/:id} : get the "id" rate.
     *
     * @param id the id of the rate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rates/{id}")
    public ResponseEntity<Rate> getRate(@PathVariable Long id) {
        log.debug("REST request to get Rate : {}", id);
        Optional<Rate> rate = rateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rate);
    }

    /**
     * {@code DELETE  /rates/:id} : delete the "id" rate.
     *
     * @param id the id of the rate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rates/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        log.debug("REST request to delete Rate : {}", id);
        rateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
