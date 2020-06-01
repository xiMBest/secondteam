package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Insuarence;
import com.secondteam.rentauto.domain.Customer;
import com.secondteam.rentauto.repository.InsuarenceRepository;
import com.secondteam.rentauto.service.InsuarenceService;
import com.secondteam.rentauto.service.dto.InsuarenceCriteria;
import com.secondteam.rentauto.service.InsuarenceQueryService;

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

import com.secondteam.rentauto.domain.enumeration.InsuarenceType;
/**
 * Integration tests for the {@link InsuarenceResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class InsuarenceResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_APPLY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_APPLY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_APPLY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_COST = 1;
    private static final Integer UPDATED_COST = 2;
    private static final Integer SMALLER_COST = 1 - 1;

    private static final InsuarenceType DEFAULT_TYPE = InsuarenceType.Gold;
    private static final InsuarenceType UPDATED_TYPE = InsuarenceType.Silver;

    @Autowired
    private InsuarenceRepository insuarenceRepository;

    @Autowired
    private InsuarenceService insuarenceService;

    @Autowired
    private InsuarenceQueryService insuarenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsuarenceMockMvc;

    private Insuarence insuarence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insuarence createEntity(EntityManager em) {
        Insuarence insuarence = new Insuarence()
            .dateApply(DEFAULT_DATE_APPLY)
            .dateEnd(DEFAULT_DATE_END)
            .cost(DEFAULT_COST)
            .type(DEFAULT_TYPE);
        return insuarence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insuarence createUpdatedEntity(EntityManager em) {
        Insuarence insuarence = new Insuarence()
            .dateApply(UPDATED_DATE_APPLY)
            .dateEnd(UPDATED_DATE_END)
            .cost(UPDATED_COST)
            .type(UPDATED_TYPE);
        return insuarence;
    }

    @BeforeEach
    public void initTest() {
        insuarence = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuarence() throws Exception {
        int databaseSizeBeforeCreate = insuarenceRepository.findAll().size();

        // Create the Insuarence
        restInsuarenceMockMvc.perform(post("/api/insuarences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuarence)))
            .andExpect(status().isCreated());

        // Validate the Insuarence in the database
        List<Insuarence> insuarenceList = insuarenceRepository.findAll();
        assertThat(insuarenceList).hasSize(databaseSizeBeforeCreate + 1);
        Insuarence testInsuarence = insuarenceList.get(insuarenceList.size() - 1);
        assertThat(testInsuarence.getDateApply()).isEqualTo(DEFAULT_DATE_APPLY);
        assertThat(testInsuarence.getDateEnd()).isEqualTo(DEFAULT_DATE_END);
        assertThat(testInsuarence.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testInsuarence.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createInsuarenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuarenceRepository.findAll().size();

        // Create the Insuarence with an existing ID
        insuarence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuarenceMockMvc.perform(post("/api/insuarences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuarence)))
            .andExpect(status().isBadRequest());

        // Validate the Insuarence in the database
        List<Insuarence> insuarenceList = insuarenceRepository.findAll();
        assertThat(insuarenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInsuarences() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList
        restInsuarenceMockMvc.perform(get("/api/insuarences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuarence.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateApply").value(hasItem(sameInstant(DEFAULT_DATE_APPLY))))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(sameInstant(DEFAULT_DATE_END))))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getInsuarence() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get the insuarence
        restInsuarenceMockMvc.perform(get("/api/insuarences/{id}", insuarence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insuarence.getId().intValue()))
            .andExpect(jsonPath("$.dateApply").value(sameInstant(DEFAULT_DATE_APPLY)))
            .andExpect(jsonPath("$.dateEnd").value(sameInstant(DEFAULT_DATE_END)))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getInsuarencesByIdFiltering() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        Long id = insuarence.getId();

        defaultInsuarenceShouldBeFound("id.equals=" + id);
        defaultInsuarenceShouldNotBeFound("id.notEquals=" + id);

        defaultInsuarenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInsuarenceShouldNotBeFound("id.greaterThan=" + id);

        defaultInsuarenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInsuarenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply equals to DEFAULT_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.equals=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply equals to UPDATED_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.equals=" + UPDATED_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply not equals to DEFAULT_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.notEquals=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply not equals to UPDATED_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.notEquals=" + UPDATED_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsInShouldWork() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply in DEFAULT_DATE_APPLY or UPDATED_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.in=" + DEFAULT_DATE_APPLY + "," + UPDATED_DATE_APPLY);

        // Get all the insuarenceList where dateApply equals to UPDATED_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.in=" + UPDATED_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply is not null
        defaultInsuarenceShouldBeFound("dateApply.specified=true");

        // Get all the insuarenceList where dateApply is null
        defaultInsuarenceShouldNotBeFound("dateApply.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply is greater than or equal to DEFAULT_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.greaterThanOrEqual=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply is greater than or equal to UPDATED_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.greaterThanOrEqual=" + UPDATED_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply is less than or equal to DEFAULT_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.lessThanOrEqual=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply is less than or equal to SMALLER_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.lessThanOrEqual=" + SMALLER_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsLessThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply is less than DEFAULT_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.lessThan=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply is less than UPDATED_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.lessThan=" + UPDATED_DATE_APPLY);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateApplyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateApply is greater than DEFAULT_DATE_APPLY
        defaultInsuarenceShouldNotBeFound("dateApply.greaterThan=" + DEFAULT_DATE_APPLY);

        // Get all the insuarenceList where dateApply is greater than SMALLER_DATE_APPLY
        defaultInsuarenceShouldBeFound("dateApply.greaterThan=" + SMALLER_DATE_APPLY);
    }


    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd equals to DEFAULT_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.equals=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd equals to UPDATED_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.equals=" + UPDATED_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd not equals to DEFAULT_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.notEquals=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd not equals to UPDATED_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.notEquals=" + UPDATED_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsInShouldWork() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd in DEFAULT_DATE_END or UPDATED_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.in=" + DEFAULT_DATE_END + "," + UPDATED_DATE_END);

        // Get all the insuarenceList where dateEnd equals to UPDATED_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.in=" + UPDATED_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd is not null
        defaultInsuarenceShouldBeFound("dateEnd.specified=true");

        // Get all the insuarenceList where dateEnd is null
        defaultInsuarenceShouldNotBeFound("dateEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd is greater than or equal to DEFAULT_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.greaterThanOrEqual=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd is greater than or equal to UPDATED_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.greaterThanOrEqual=" + UPDATED_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd is less than or equal to DEFAULT_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.lessThanOrEqual=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd is less than or equal to SMALLER_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.lessThanOrEqual=" + SMALLER_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsLessThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd is less than DEFAULT_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.lessThan=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd is less than UPDATED_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.lessThan=" + UPDATED_DATE_END);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByDateEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where dateEnd is greater than DEFAULT_DATE_END
        defaultInsuarenceShouldNotBeFound("dateEnd.greaterThan=" + DEFAULT_DATE_END);

        // Get all the insuarenceList where dateEnd is greater than SMALLER_DATE_END
        defaultInsuarenceShouldBeFound("dateEnd.greaterThan=" + SMALLER_DATE_END);
    }


    @Test
    @Transactional
    public void getAllInsuarencesByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost equals to DEFAULT_COST
        defaultInsuarenceShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the insuarenceList where cost equals to UPDATED_COST
        defaultInsuarenceShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost not equals to DEFAULT_COST
        defaultInsuarenceShouldNotBeFound("cost.notEquals=" + DEFAULT_COST);

        // Get all the insuarenceList where cost not equals to UPDATED_COST
        defaultInsuarenceShouldBeFound("cost.notEquals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsInShouldWork() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost in DEFAULT_COST or UPDATED_COST
        defaultInsuarenceShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the insuarenceList where cost equals to UPDATED_COST
        defaultInsuarenceShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost is not null
        defaultInsuarenceShouldBeFound("cost.specified=true");

        // Get all the insuarenceList where cost is null
        defaultInsuarenceShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost is greater than or equal to DEFAULT_COST
        defaultInsuarenceShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the insuarenceList where cost is greater than or equal to UPDATED_COST
        defaultInsuarenceShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost is less than or equal to DEFAULT_COST
        defaultInsuarenceShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the insuarenceList where cost is less than or equal to SMALLER_COST
        defaultInsuarenceShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost is less than DEFAULT_COST
        defaultInsuarenceShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the insuarenceList where cost is less than UPDATED_COST
        defaultInsuarenceShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where cost is greater than DEFAULT_COST
        defaultInsuarenceShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the insuarenceList where cost is greater than SMALLER_COST
        defaultInsuarenceShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }


    @Test
    @Transactional
    public void getAllInsuarencesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where type equals to DEFAULT_TYPE
        defaultInsuarenceShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the insuarenceList where type equals to UPDATED_TYPE
        defaultInsuarenceShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where type not equals to DEFAULT_TYPE
        defaultInsuarenceShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the insuarenceList where type not equals to UPDATED_TYPE
        defaultInsuarenceShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultInsuarenceShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the insuarenceList where type equals to UPDATED_TYPE
        defaultInsuarenceShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllInsuarencesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);

        // Get all the insuarenceList where type is not null
        defaultInsuarenceShouldBeFound("type.specified=true");

        // Get all the insuarenceList where type is null
        defaultInsuarenceShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsuarencesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        insuarenceRepository.saveAndFlush(insuarence);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        insuarence.setCustomer(customer);
        insuarenceRepository.saveAndFlush(insuarence);
        Long customerId = customer.getId();

        // Get all the insuarenceList where customer equals to customerId
        defaultInsuarenceShouldBeFound("customerId.equals=" + customerId);

        // Get all the insuarenceList where customer equals to customerId + 1
        defaultInsuarenceShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInsuarenceShouldBeFound(String filter) throws Exception {
        restInsuarenceMockMvc.perform(get("/api/insuarences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuarence.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateApply").value(hasItem(sameInstant(DEFAULT_DATE_APPLY))))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(sameInstant(DEFAULT_DATE_END))))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restInsuarenceMockMvc.perform(get("/api/insuarences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInsuarenceShouldNotBeFound(String filter) throws Exception {
        restInsuarenceMockMvc.perform(get("/api/insuarences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInsuarenceMockMvc.perform(get("/api/insuarences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInsuarence() throws Exception {
        // Get the insuarence
        restInsuarenceMockMvc.perform(get("/api/insuarences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuarence() throws Exception {
        // Initialize the database
        insuarenceService.save(insuarence);

        int databaseSizeBeforeUpdate = insuarenceRepository.findAll().size();

        // Update the insuarence
        Insuarence updatedInsuarence = insuarenceRepository.findById(insuarence.getId()).get();
        // Disconnect from session so that the updates on updatedInsuarence are not directly saved in db
        em.detach(updatedInsuarence);
        updatedInsuarence
            .dateApply(UPDATED_DATE_APPLY)
            .dateEnd(UPDATED_DATE_END)
            .cost(UPDATED_COST)
            .type(UPDATED_TYPE);

        restInsuarenceMockMvc.perform(put("/api/insuarences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuarence)))
            .andExpect(status().isOk());

        // Validate the Insuarence in the database
        List<Insuarence> insuarenceList = insuarenceRepository.findAll();
        assertThat(insuarenceList).hasSize(databaseSizeBeforeUpdate);
        Insuarence testInsuarence = insuarenceList.get(insuarenceList.size() - 1);
        assertThat(testInsuarence.getDateApply()).isEqualTo(UPDATED_DATE_APPLY);
        assertThat(testInsuarence.getDateEnd()).isEqualTo(UPDATED_DATE_END);
        assertThat(testInsuarence.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testInsuarence.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuarence() throws Exception {
        int databaseSizeBeforeUpdate = insuarenceRepository.findAll().size();

        // Create the Insuarence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuarenceMockMvc.perform(put("/api/insuarences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(insuarence)))
            .andExpect(status().isBadRequest());

        // Validate the Insuarence in the database
        List<Insuarence> insuarenceList = insuarenceRepository.findAll();
        assertThat(insuarenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInsuarence() throws Exception {
        // Initialize the database
        insuarenceService.save(insuarence);

        int databaseSizeBeforeDelete = insuarenceRepository.findAll().size();

        // Delete the insuarence
        restInsuarenceMockMvc.perform(delete("/api/insuarences/{id}", insuarence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insuarence> insuarenceList = insuarenceRepository.findAll();
        assertThat(insuarenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
