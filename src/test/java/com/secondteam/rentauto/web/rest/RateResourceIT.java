package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Rate;
import com.secondteam.rentauto.domain.Autopark;
import com.secondteam.rentauto.repository.RateRepository;
import com.secondteam.rentauto.service.RateService;
import com.secondteam.rentauto.service.dto.RateCriteria;
import com.secondteam.rentauto.service.RateQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.secondteam.rentauto.domain.enumeration.Ratestars;
/**
 * Integration tests for the {@link RateResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RateResourceIT {

    private static final Ratestars DEFAULT_RAITING = Ratestars.ONE_STAR;
    private static final Ratestars UPDATED_RAITING = Ratestars.FIVE_STARS;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateService rateService;

    @Autowired
    private RateQueryService rateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRateMockMvc;

    private Rate rate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rate createEntity(EntityManager em) {
        Rate rate = new Rate()
            .raiting(DEFAULT_RAITING);
        return rate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rate createUpdatedEntity(EntityManager em) {
        Rate rate = new Rate()
            .raiting(UPDATED_RAITING);
        return rate;
    }

    @BeforeEach
    public void initTest() {
        rate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRate() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate
        restRateMockMvc.perform(post("/api/rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rate)))
            .andExpect(status().isCreated());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate + 1);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getRaiting()).isEqualTo(DEFAULT_RAITING);
    }

    @Test
    @Transactional
    public void createRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate with an existing ID
        rate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateMockMvc.perform(post("/api/rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rate)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRates() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList
        restRateMockMvc.perform(get("/api/rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId().intValue())))
            .andExpect(jsonPath("$.[*].raiting").value(hasItem(DEFAULT_RAITING.toString())));
    }
    
    @Test
    @Transactional
    public void getRate() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", rate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rate.getId().intValue()))
            .andExpect(jsonPath("$.raiting").value(DEFAULT_RAITING.toString()));
    }


    @Test
    @Transactional
    public void getRatesByIdFiltering() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        Long id = rate.getId();

        defaultRateShouldBeFound("id.equals=" + id);
        defaultRateShouldNotBeFound("id.notEquals=" + id);

        defaultRateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRateShouldNotBeFound("id.greaterThan=" + id);

        defaultRateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRatesByRaitingIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where raiting equals to DEFAULT_RAITING
        defaultRateShouldBeFound("raiting.equals=" + DEFAULT_RAITING);

        // Get all the rateList where raiting equals to UPDATED_RAITING
        defaultRateShouldNotBeFound("raiting.equals=" + UPDATED_RAITING);
    }

    @Test
    @Transactional
    public void getAllRatesByRaitingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where raiting not equals to DEFAULT_RAITING
        defaultRateShouldNotBeFound("raiting.notEquals=" + DEFAULT_RAITING);

        // Get all the rateList where raiting not equals to UPDATED_RAITING
        defaultRateShouldBeFound("raiting.notEquals=" + UPDATED_RAITING);
    }

    @Test
    @Transactional
    public void getAllRatesByRaitingIsInShouldWork() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where raiting in DEFAULT_RAITING or UPDATED_RAITING
        defaultRateShouldBeFound("raiting.in=" + DEFAULT_RAITING + "," + UPDATED_RAITING);

        // Get all the rateList where raiting equals to UPDATED_RAITING
        defaultRateShouldNotBeFound("raiting.in=" + UPDATED_RAITING);
    }

    @Test
    @Transactional
    public void getAllRatesByRaitingIsNullOrNotNull() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where raiting is not null
        defaultRateShouldBeFound("raiting.specified=true");

        // Get all the rateList where raiting is null
        defaultRateShouldNotBeFound("raiting.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatesByAutoparkIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);
        Autopark autopark = AutoparkResourceIT.createEntity(em);
        em.persist(autopark);
        em.flush();
        rate.setAutopark(autopark);
        rateRepository.saveAndFlush(rate);
        Long autoparkId = autopark.getId();

        // Get all the rateList where autopark equals to autoparkId
        defaultRateShouldBeFound("autoparkId.equals=" + autoparkId);

        // Get all the rateList where autopark equals to autoparkId + 1
        defaultRateShouldNotBeFound("autoparkId.equals=" + (autoparkId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRateShouldBeFound(String filter) throws Exception {
        restRateMockMvc.perform(get("/api/rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId().intValue())))
            .andExpect(jsonPath("$.[*].raiting").value(hasItem(DEFAULT_RAITING.toString())));

        // Check, that the count call also returns 1
        restRateMockMvc.perform(get("/api/rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRateShouldNotBeFound(String filter) throws Exception {
        restRateMockMvc.perform(get("/api/rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRateMockMvc.perform(get("/api/rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRate() throws Exception {
        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRate() throws Exception {
        // Initialize the database
        rateService.save(rate);

        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Update the rate
        Rate updatedRate = rateRepository.findById(rate.getId()).get();
        // Disconnect from session so that the updates on updatedRate are not directly saved in db
        em.detach(updatedRate);
        updatedRate
            .raiting(UPDATED_RAITING);

        restRateMockMvc.perform(put("/api/rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRate)))
            .andExpect(status().isOk());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getRaiting()).isEqualTo(UPDATED_RAITING);
    }

    @Test
    @Transactional
    public void updateNonExistingRate() throws Exception {
        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Create the Rate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRateMockMvc.perform(put("/api/rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rate)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRate() throws Exception {
        // Initialize the database
        rateService.save(rate);

        int databaseSizeBeforeDelete = rateRepository.findAll().size();

        // Delete the rate
        restRateMockMvc.perform(delete("/api/rates/{id}", rate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
