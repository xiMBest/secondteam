package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Fixcar;
import com.secondteam.rentauto.domain.Autopark;
import com.secondteam.rentauto.repository.FixcarRepository;
import com.secondteam.rentauto.service.FixcarService;
import com.secondteam.rentauto.service.dto.FixcarCriteria;
import com.secondteam.rentauto.service.FixcarQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.secondteam.rentauto.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FixcarResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FixcarResourceIT {

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;
    private static final Integer SMALLER_PRICE = 1 - 1;

    private static final ZonedDateTime DEFAULT_DATE_FIXING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIXING = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_FIXING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FixcarRepository fixcarRepository;

    @Mock
    private FixcarRepository fixcarRepositoryMock;

    @Mock
    private FixcarService fixcarServiceMock;

    @Autowired
    private FixcarService fixcarService;

    @Autowired
    private FixcarQueryService fixcarQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFixcarMockMvc;

    private Fixcar fixcar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fixcar createEntity(EntityManager em) {
        Fixcar fixcar = new Fixcar()
            .price(DEFAULT_PRICE)
            .dateFixing(DEFAULT_DATE_FIXING)
            .description(DEFAULT_DESCRIPTION);
        return fixcar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fixcar createUpdatedEntity(EntityManager em) {
        Fixcar fixcar = new Fixcar()
            .price(UPDATED_PRICE)
            .dateFixing(UPDATED_DATE_FIXING)
            .description(UPDATED_DESCRIPTION);
        return fixcar;
    }

    @BeforeEach
    public void initTest() {
        fixcar = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixcar() throws Exception {
        int databaseSizeBeforeCreate = fixcarRepository.findAll().size();

        // Create the Fixcar
        restFixcarMockMvc.perform(post("/api/fixcars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fixcar)))
            .andExpect(status().isCreated());

        // Validate the Fixcar in the database
        List<Fixcar> fixcarList = fixcarRepository.findAll();
        assertThat(fixcarList).hasSize(databaseSizeBeforeCreate + 1);
        Fixcar testFixcar = fixcarList.get(fixcarList.size() - 1);
        assertThat(testFixcar.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFixcar.getDateFixing()).isEqualTo(DEFAULT_DATE_FIXING);
        assertThat(testFixcar.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFixcarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixcarRepository.findAll().size();

        // Create the Fixcar with an existing ID
        fixcar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixcarMockMvc.perform(post("/api/fixcars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fixcar)))
            .andExpect(status().isBadRequest());

        // Validate the Fixcar in the database
        List<Fixcar> fixcarList = fixcarRepository.findAll();
        assertThat(fixcarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFixcars() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList
        restFixcarMockMvc.perform(get("/api/fixcars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixcar.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].dateFixing").value(hasItem(sameInstant(DEFAULT_DATE_FIXING))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFixcarsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fixcarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFixcarMockMvc.perform(get("/api/fixcars?eagerload=true"))
            .andExpect(status().isOk());

        verify(fixcarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFixcarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fixcarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFixcarMockMvc.perform(get("/api/fixcars?eagerload=true"))
            .andExpect(status().isOk());

        verify(fixcarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFixcar() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get the fixcar
        restFixcarMockMvc.perform(get("/api/fixcars/{id}", fixcar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fixcar.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.dateFixing").value(sameInstant(DEFAULT_DATE_FIXING)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getFixcarsByIdFiltering() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        Long id = fixcar.getId();

        defaultFixcarShouldBeFound("id.equals=" + id);
        defaultFixcarShouldNotBeFound("id.notEquals=" + id);

        defaultFixcarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFixcarShouldNotBeFound("id.greaterThan=" + id);

        defaultFixcarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFixcarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFixcarsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price equals to DEFAULT_PRICE
        defaultFixcarShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the fixcarList where price equals to UPDATED_PRICE
        defaultFixcarShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price not equals to DEFAULT_PRICE
        defaultFixcarShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the fixcarList where price not equals to UPDATED_PRICE
        defaultFixcarShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultFixcarShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the fixcarList where price equals to UPDATED_PRICE
        defaultFixcarShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price is not null
        defaultFixcarShouldBeFound("price.specified=true");

        // Get all the fixcarList where price is null
        defaultFixcarShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price is greater than or equal to DEFAULT_PRICE
        defaultFixcarShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the fixcarList where price is greater than or equal to UPDATED_PRICE
        defaultFixcarShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price is less than or equal to DEFAULT_PRICE
        defaultFixcarShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the fixcarList where price is less than or equal to SMALLER_PRICE
        defaultFixcarShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price is less than DEFAULT_PRICE
        defaultFixcarShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the fixcarList where price is less than UPDATED_PRICE
        defaultFixcarShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFixcarsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where price is greater than DEFAULT_PRICE
        defaultFixcarShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the fixcarList where price is greater than SMALLER_PRICE
        defaultFixcarShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing equals to DEFAULT_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.equals=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing equals to UPDATED_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.equals=" + UPDATED_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing not equals to DEFAULT_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.notEquals=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing not equals to UPDATED_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.notEquals=" + UPDATED_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsInShouldWork() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing in DEFAULT_DATE_FIXING or UPDATED_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.in=" + DEFAULT_DATE_FIXING + "," + UPDATED_DATE_FIXING);

        // Get all the fixcarList where dateFixing equals to UPDATED_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.in=" + UPDATED_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing is not null
        defaultFixcarShouldBeFound("dateFixing.specified=true");

        // Get all the fixcarList where dateFixing is null
        defaultFixcarShouldNotBeFound("dateFixing.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing is greater than or equal to DEFAULT_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.greaterThanOrEqual=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing is greater than or equal to UPDATED_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.greaterThanOrEqual=" + UPDATED_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing is less than or equal to DEFAULT_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.lessThanOrEqual=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing is less than or equal to SMALLER_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.lessThanOrEqual=" + SMALLER_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsLessThanSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing is less than DEFAULT_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.lessThan=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing is less than UPDATED_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.lessThan=" + UPDATED_DATE_FIXING);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDateFixingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where dateFixing is greater than DEFAULT_DATE_FIXING
        defaultFixcarShouldNotBeFound("dateFixing.greaterThan=" + DEFAULT_DATE_FIXING);

        // Get all the fixcarList where dateFixing is greater than SMALLER_DATE_FIXING
        defaultFixcarShouldBeFound("dateFixing.greaterThan=" + SMALLER_DATE_FIXING);
    }


    @Test
    @Transactional
    public void getAllFixcarsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description equals to DEFAULT_DESCRIPTION
        defaultFixcarShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the fixcarList where description equals to UPDATED_DESCRIPTION
        defaultFixcarShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description not equals to DEFAULT_DESCRIPTION
        defaultFixcarShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the fixcarList where description not equals to UPDATED_DESCRIPTION
        defaultFixcarShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFixcarShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the fixcarList where description equals to UPDATED_DESCRIPTION
        defaultFixcarShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description is not null
        defaultFixcarShouldBeFound("description.specified=true");

        // Get all the fixcarList where description is null
        defaultFixcarShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllFixcarsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description contains DEFAULT_DESCRIPTION
        defaultFixcarShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the fixcarList where description contains UPDATED_DESCRIPTION
        defaultFixcarShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixcarsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);

        // Get all the fixcarList where description does not contain DEFAULT_DESCRIPTION
        defaultFixcarShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the fixcarList where description does not contain UPDATED_DESCRIPTION
        defaultFixcarShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllFixcarsByAutoparksIsEqualToSomething() throws Exception {
        // Initialize the database
        fixcarRepository.saveAndFlush(fixcar);
        Autopark autoparks = AutoparkResourceIT.createEntity(em);
        em.persist(autoparks);
        em.flush();
        fixcar.addAutoparks(autoparks);
        fixcarRepository.saveAndFlush(fixcar);
        Long autoparksId = autoparks.getId();

        // Get all the fixcarList where autoparks equals to autoparksId
        defaultFixcarShouldBeFound("autoparksId.equals=" + autoparksId);

        // Get all the fixcarList where autoparks equals to autoparksId + 1
        defaultFixcarShouldNotBeFound("autoparksId.equals=" + (autoparksId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFixcarShouldBeFound(String filter) throws Exception {
        restFixcarMockMvc.perform(get("/api/fixcars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixcar.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].dateFixing").value(hasItem(sameInstant(DEFAULT_DATE_FIXING))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restFixcarMockMvc.perform(get("/api/fixcars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFixcarShouldNotBeFound(String filter) throws Exception {
        restFixcarMockMvc.perform(get("/api/fixcars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFixcarMockMvc.perform(get("/api/fixcars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFixcar() throws Exception {
        // Get the fixcar
        restFixcarMockMvc.perform(get("/api/fixcars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixcar() throws Exception {
        // Initialize the database
        fixcarService.save(fixcar);

        int databaseSizeBeforeUpdate = fixcarRepository.findAll().size();

        // Update the fixcar
        Fixcar updatedFixcar = fixcarRepository.findById(fixcar.getId()).get();
        // Disconnect from session so that the updates on updatedFixcar are not directly saved in db
        em.detach(updatedFixcar);
        updatedFixcar
            .price(UPDATED_PRICE)
            .dateFixing(UPDATED_DATE_FIXING)
            .description(UPDATED_DESCRIPTION);

        restFixcarMockMvc.perform(put("/api/fixcars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFixcar)))
            .andExpect(status().isOk());

        // Validate the Fixcar in the database
        List<Fixcar> fixcarList = fixcarRepository.findAll();
        assertThat(fixcarList).hasSize(databaseSizeBeforeUpdate);
        Fixcar testFixcar = fixcarList.get(fixcarList.size() - 1);
        assertThat(testFixcar.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFixcar.getDateFixing()).isEqualTo(UPDATED_DATE_FIXING);
        assertThat(testFixcar.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFixcar() throws Exception {
        int databaseSizeBeforeUpdate = fixcarRepository.findAll().size();

        // Create the Fixcar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixcarMockMvc.perform(put("/api/fixcars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fixcar)))
            .andExpect(status().isBadRequest());

        // Validate the Fixcar in the database
        List<Fixcar> fixcarList = fixcarRepository.findAll();
        assertThat(fixcarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFixcar() throws Exception {
        // Initialize the database
        fixcarService.save(fixcar);

        int databaseSizeBeforeDelete = fixcarRepository.findAll().size();

        // Delete the fixcar
        restFixcarMockMvc.perform(delete("/api/fixcars/{id}", fixcar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fixcar> fixcarList = fixcarRepository.findAll();
        assertThat(fixcarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
