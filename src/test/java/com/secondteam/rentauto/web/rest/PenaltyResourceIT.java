package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Penalty;
import com.secondteam.rentauto.domain.Customer;
import com.secondteam.rentauto.repository.PenaltyRepository;
import com.secondteam.rentauto.service.PenaltyService;
import com.secondteam.rentauto.service.dto.PenaltyCriteria;
import com.secondteam.rentauto.service.PenaltyQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.secondteam.rentauto.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PenaltyResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PenaltyResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_PENALTY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_PENALTY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_PENALTY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;
    private static final Integer SMALLER_PRICE = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private PenaltyService penaltyService;

    @Autowired
    private PenaltyQueryService penaltyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPenaltyMockMvc;

    private Penalty penalty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Penalty createEntity(EntityManager em) {
        Penalty penalty = new Penalty()
            .datePenalty(DEFAULT_DATE_PENALTY)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION);
        return penalty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Penalty createUpdatedEntity(EntityManager em) {
        Penalty penalty = new Penalty()
            .datePenalty(UPDATED_DATE_PENALTY)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);
        return penalty;
    }

    @BeforeEach
    public void initTest() {
        penalty = createEntity(em);
    }

    @Test
    @Transactional
    public void createPenalty() throws Exception {
        int databaseSizeBeforeCreate = penaltyRepository.findAll().size();

        // Create the Penalty
        restPenaltyMockMvc.perform(post("/api/penalties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(penalty)))
            .andExpect(status().isCreated());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeCreate + 1);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getDatePenalty()).isEqualTo(DEFAULT_DATE_PENALTY);
        assertThat(testPenalty.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPenalty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPenaltyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = penaltyRepository.findAll().size();

        // Create the Penalty with an existing ID
        penalty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPenaltyMockMvc.perform(post("/api/penalties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(penalty)))
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPenalties() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList
        restPenaltyMockMvc.perform(get("/api/penalties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(penalty.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePenalty").value(hasItem(sameInstant(DEFAULT_DATE_PENALTY))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPenalty() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get the penalty
        restPenaltyMockMvc.perform(get("/api/penalties/{id}", penalty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(penalty.getId().intValue()))
            .andExpect(jsonPath("$.datePenalty").value(sameInstant(DEFAULT_DATE_PENALTY)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getPenaltiesByIdFiltering() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        Long id = penalty.getId();

        defaultPenaltyShouldBeFound("id.equals=" + id);
        defaultPenaltyShouldNotBeFound("id.notEquals=" + id);

        defaultPenaltyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPenaltyShouldNotBeFound("id.greaterThan=" + id);

        defaultPenaltyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPenaltyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty equals to DEFAULT_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.equals=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty equals to UPDATED_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.equals=" + UPDATED_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty not equals to DEFAULT_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.notEquals=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty not equals to UPDATED_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.notEquals=" + UPDATED_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsInShouldWork() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty in DEFAULT_DATE_PENALTY or UPDATED_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.in=" + DEFAULT_DATE_PENALTY + "," + UPDATED_DATE_PENALTY);

        // Get all the penaltyList where datePenalty equals to UPDATED_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.in=" + UPDATED_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsNullOrNotNull() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty is not null
        defaultPenaltyShouldBeFound("datePenalty.specified=true");

        // Get all the penaltyList where datePenalty is null
        defaultPenaltyShouldNotBeFound("datePenalty.specified=false");
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty is greater than or equal to DEFAULT_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.greaterThanOrEqual=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty is greater than or equal to UPDATED_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.greaterThanOrEqual=" + UPDATED_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty is less than or equal to DEFAULT_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.lessThanOrEqual=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty is less than or equal to SMALLER_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.lessThanOrEqual=" + SMALLER_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsLessThanSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty is less than DEFAULT_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.lessThan=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty is less than UPDATED_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.lessThan=" + UPDATED_DATE_PENALTY);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDatePenaltyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where datePenalty is greater than DEFAULT_DATE_PENALTY
        defaultPenaltyShouldNotBeFound("datePenalty.greaterThan=" + DEFAULT_DATE_PENALTY);

        // Get all the penaltyList where datePenalty is greater than SMALLER_DATE_PENALTY
        defaultPenaltyShouldBeFound("datePenalty.greaterThan=" + SMALLER_DATE_PENALTY);
    }


    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price equals to DEFAULT_PRICE
        defaultPenaltyShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the penaltyList where price equals to UPDATED_PRICE
        defaultPenaltyShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price not equals to DEFAULT_PRICE
        defaultPenaltyShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the penaltyList where price not equals to UPDATED_PRICE
        defaultPenaltyShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPenaltyShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the penaltyList where price equals to UPDATED_PRICE
        defaultPenaltyShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price is not null
        defaultPenaltyShouldBeFound("price.specified=true");

        // Get all the penaltyList where price is null
        defaultPenaltyShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price is greater than or equal to DEFAULT_PRICE
        defaultPenaltyShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the penaltyList where price is greater than or equal to UPDATED_PRICE
        defaultPenaltyShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price is less than or equal to DEFAULT_PRICE
        defaultPenaltyShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the penaltyList where price is less than or equal to SMALLER_PRICE
        defaultPenaltyShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price is less than DEFAULT_PRICE
        defaultPenaltyShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the penaltyList where price is less than UPDATED_PRICE
        defaultPenaltyShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where price is greater than DEFAULT_PRICE
        defaultPenaltyShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the penaltyList where price is greater than SMALLER_PRICE
        defaultPenaltyShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllPenaltiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description equals to DEFAULT_DESCRIPTION
        defaultPenaltyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the penaltyList where description equals to UPDATED_DESCRIPTION
        defaultPenaltyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description not equals to DEFAULT_DESCRIPTION
        defaultPenaltyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the penaltyList where description not equals to UPDATED_DESCRIPTION
        defaultPenaltyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPenaltyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the penaltyList where description equals to UPDATED_DESCRIPTION
        defaultPenaltyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description is not null
        defaultPenaltyShouldBeFound("description.specified=true");

        // Get all the penaltyList where description is null
        defaultPenaltyShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPenaltiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description contains DEFAULT_DESCRIPTION
        defaultPenaltyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the penaltyList where description contains UPDATED_DESCRIPTION
        defaultPenaltyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPenaltiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList where description does not contain DEFAULT_DESCRIPTION
        defaultPenaltyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the penaltyList where description does not contain UPDATED_DESCRIPTION
        defaultPenaltyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPenaltiesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        penalty.setCustomer(customer);
        penaltyRepository.saveAndFlush(penalty);
        Long customerId = customer.getId();

        // Get all the penaltyList where customer equals to customerId
        defaultPenaltyShouldBeFound("customerId.equals=" + customerId);

        // Get all the penaltyList where customer equals to customerId + 1
        defaultPenaltyShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPenaltyShouldBeFound(String filter) throws Exception {
        restPenaltyMockMvc.perform(get("/api/penalties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(penalty.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePenalty").value(hasItem(sameInstant(DEFAULT_DATE_PENALTY))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restPenaltyMockMvc.perform(get("/api/penalties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPenaltyShouldNotBeFound(String filter) throws Exception {
        restPenaltyMockMvc.perform(get("/api/penalties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPenaltyMockMvc.perform(get("/api/penalties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPenalty() throws Exception {
        // Get the penalty
        restPenaltyMockMvc.perform(get("/api/penalties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePenalty() throws Exception {
        // Initialize the database
        penaltyService.save(penalty);

        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();

        // Update the penalty
        Penalty updatedPenalty = penaltyRepository.findById(penalty.getId()).get();
        // Disconnect from session so that the updates on updatedPenalty are not directly saved in db
        em.detach(updatedPenalty);
        updatedPenalty
            .datePenalty(UPDATED_DATE_PENALTY)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);

        restPenaltyMockMvc.perform(put("/api/penalties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPenalty)))
            .andExpect(status().isOk());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getDatePenalty()).isEqualTo(UPDATED_DATE_PENALTY);
        assertThat(testPenalty.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPenalty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();

        // Create the Penalty

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPenaltyMockMvc.perform(put("/api/penalties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(penalty)))
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePenalty() throws Exception {
        // Initialize the database
        penaltyService.save(penalty);

        int databaseSizeBeforeDelete = penaltyRepository.findAll().size();

        // Delete the penalty
        restPenaltyMockMvc.perform(delete("/api/penalties/{id}", penalty.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
