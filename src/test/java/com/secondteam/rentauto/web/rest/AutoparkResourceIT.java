package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Autopark;
import com.secondteam.rentauto.domain.Rate;
import com.secondteam.rentauto.domain.Fixcar;
import com.secondteam.rentauto.repository.AutoparkRepository;
import com.secondteam.rentauto.service.AutoparkService;
import com.secondteam.rentauto.service.dto.AutoparkCriteria;
import com.secondteam.rentauto.service.AutoparkQueryService;

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

import com.secondteam.rentauto.domain.enumeration.CarType;
/**
 * Integration tests for the {@link AutoparkResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AutoparkResourceIT {

    private static final String DEFAULT_MARK = "AAAAAAAAAA";
    private static final String UPDATED_MARK = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRE_PRICE = 1;
    private static final Integer UPDATED_PRE_PRICE = 2;
    private static final Integer SMALLER_PRE_PRICE = 1 - 1;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final CarType DEFAULT_TYPE = CarType.Car;
    private static final CarType UPDATED_TYPE = CarType.SUV;

    private static final Integer DEFAULT_PLEDGE = 1;
    private static final Integer UPDATED_PLEDGE = 2;
    private static final Integer SMALLER_PLEDGE = 1 - 1;

    private static final Boolean DEFAULT_STATUS_AVAILEBLE = false;
    private static final Boolean UPDATED_STATUS_AVAILEBLE = true;

    @Autowired
    private AutoparkRepository autoparkRepository;

    @Autowired
    private AutoparkService autoparkService;

    @Autowired
    private AutoparkQueryService autoparkQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoparkMockMvc;

    private Autopark autopark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autopark createEntity(EntityManager em) {
        Autopark autopark = new Autopark()
            .mark(DEFAULT_MARK)
            .model(DEFAULT_MODEL)
            .prePrice(DEFAULT_PRE_PRICE)
            .color(DEFAULT_COLOR)
            .type(DEFAULT_TYPE)
            .pledge(DEFAULT_PLEDGE)
            .statusAvaileble(DEFAULT_STATUS_AVAILEBLE);
        return autopark;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autopark createUpdatedEntity(EntityManager em) {
        Autopark autopark = new Autopark()
            .mark(UPDATED_MARK)
            .model(UPDATED_MODEL)
            .prePrice(UPDATED_PRE_PRICE)
            .color(UPDATED_COLOR)
            .type(UPDATED_TYPE)
            .pledge(UPDATED_PLEDGE)
            .statusAvaileble(UPDATED_STATUS_AVAILEBLE);
        return autopark;
    }

    @BeforeEach
    public void initTest() {
        autopark = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutopark() throws Exception {
        int databaseSizeBeforeCreate = autoparkRepository.findAll().size();

        // Create the Autopark
        restAutoparkMockMvc.perform(post("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isCreated());

        // Validate the Autopark in the database
        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeCreate + 1);
        Autopark testAutopark = autoparkList.get(autoparkList.size() - 1);
        assertThat(testAutopark.getMark()).isEqualTo(DEFAULT_MARK);
        assertThat(testAutopark.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAutopark.getPrePrice()).isEqualTo(DEFAULT_PRE_PRICE);
        assertThat(testAutopark.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testAutopark.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAutopark.getPledge()).isEqualTo(DEFAULT_PLEDGE);
        assertThat(testAutopark.isStatusAvaileble()).isEqualTo(DEFAULT_STATUS_AVAILEBLE);
    }

    @Test
    @Transactional
    public void createAutoparkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autoparkRepository.findAll().size();

        // Create the Autopark with an existing ID
        autopark.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoparkMockMvc.perform(post("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isBadRequest());

        // Validate the Autopark in the database
        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMarkIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoparkRepository.findAll().size();
        // set the field null
        autopark.setMark(null);

        // Create the Autopark, which fails.

        restAutoparkMockMvc.perform(post("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isBadRequest());

        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoparkRepository.findAll().size();
        // set the field null
        autopark.setModel(null);

        // Create the Autopark, which fails.

        restAutoparkMockMvc.perform(post("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isBadRequest());

        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoparkRepository.findAll().size();
        // set the field null
        autopark.setColor(null);

        // Create the Autopark, which fails.

        restAutoparkMockMvc.perform(post("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isBadRequest());

        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutoparks() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList
        restAutoparkMockMvc.perform(get("/api/autoparks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autopark.getId().intValue())))
            .andExpect(jsonPath("$.[*].mark").value(hasItem(DEFAULT_MARK)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].prePrice").value(hasItem(DEFAULT_PRE_PRICE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pledge").value(hasItem(DEFAULT_PLEDGE)))
            .andExpect(jsonPath("$.[*].statusAvaileble").value(hasItem(DEFAULT_STATUS_AVAILEBLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAutopark() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get the autopark
        restAutoparkMockMvc.perform(get("/api/autoparks/{id}", autopark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autopark.getId().intValue()))
            .andExpect(jsonPath("$.mark").value(DEFAULT_MARK))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.prePrice").value(DEFAULT_PRE_PRICE))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.pledge").value(DEFAULT_PLEDGE))
            .andExpect(jsonPath("$.statusAvaileble").value(DEFAULT_STATUS_AVAILEBLE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAutoparksByIdFiltering() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        Long id = autopark.getId();

        defaultAutoparkShouldBeFound("id.equals=" + id);
        defaultAutoparkShouldNotBeFound("id.notEquals=" + id);

        defaultAutoparkShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAutoparkShouldNotBeFound("id.greaterThan=" + id);

        defaultAutoparkShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAutoparkShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAutoparksByMarkIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark equals to DEFAULT_MARK
        defaultAutoparkShouldBeFound("mark.equals=" + DEFAULT_MARK);

        // Get all the autoparkList where mark equals to UPDATED_MARK
        defaultAutoparkShouldNotBeFound("mark.equals=" + UPDATED_MARK);
    }

    @Test
    @Transactional
    public void getAllAutoparksByMarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark not equals to DEFAULT_MARK
        defaultAutoparkShouldNotBeFound("mark.notEquals=" + DEFAULT_MARK);

        // Get all the autoparkList where mark not equals to UPDATED_MARK
        defaultAutoparkShouldBeFound("mark.notEquals=" + UPDATED_MARK);
    }

    @Test
    @Transactional
    public void getAllAutoparksByMarkIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark in DEFAULT_MARK or UPDATED_MARK
        defaultAutoparkShouldBeFound("mark.in=" + DEFAULT_MARK + "," + UPDATED_MARK);

        // Get all the autoparkList where mark equals to UPDATED_MARK
        defaultAutoparkShouldNotBeFound("mark.in=" + UPDATED_MARK);
    }

    @Test
    @Transactional
    public void getAllAutoparksByMarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark is not null
        defaultAutoparkShouldBeFound("mark.specified=true");

        // Get all the autoparkList where mark is null
        defaultAutoparkShouldNotBeFound("mark.specified=false");
    }
                @Test
    @Transactional
    public void getAllAutoparksByMarkContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark contains DEFAULT_MARK
        defaultAutoparkShouldBeFound("mark.contains=" + DEFAULT_MARK);

        // Get all the autoparkList where mark contains UPDATED_MARK
        defaultAutoparkShouldNotBeFound("mark.contains=" + UPDATED_MARK);
    }

    @Test
    @Transactional
    public void getAllAutoparksByMarkNotContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where mark does not contain DEFAULT_MARK
        defaultAutoparkShouldNotBeFound("mark.doesNotContain=" + DEFAULT_MARK);

        // Get all the autoparkList where mark does not contain UPDATED_MARK
        defaultAutoparkShouldBeFound("mark.doesNotContain=" + UPDATED_MARK);
    }


    @Test
    @Transactional
    public void getAllAutoparksByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model equals to DEFAULT_MODEL
        defaultAutoparkShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the autoparkList where model equals to UPDATED_MODEL
        defaultAutoparkShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllAutoparksByModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model not equals to DEFAULT_MODEL
        defaultAutoparkShouldNotBeFound("model.notEquals=" + DEFAULT_MODEL);

        // Get all the autoparkList where model not equals to UPDATED_MODEL
        defaultAutoparkShouldBeFound("model.notEquals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllAutoparksByModelIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultAutoparkShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the autoparkList where model equals to UPDATED_MODEL
        defaultAutoparkShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllAutoparksByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model is not null
        defaultAutoparkShouldBeFound("model.specified=true");

        // Get all the autoparkList where model is null
        defaultAutoparkShouldNotBeFound("model.specified=false");
    }
                @Test
    @Transactional
    public void getAllAutoparksByModelContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model contains DEFAULT_MODEL
        defaultAutoparkShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the autoparkList where model contains UPDATED_MODEL
        defaultAutoparkShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllAutoparksByModelNotContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where model does not contain DEFAULT_MODEL
        defaultAutoparkShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the autoparkList where model does not contain UPDATED_MODEL
        defaultAutoparkShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }


    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice equals to DEFAULT_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.equals=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice equals to UPDATED_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.equals=" + UPDATED_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice not equals to DEFAULT_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.notEquals=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice not equals to UPDATED_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.notEquals=" + UPDATED_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice in DEFAULT_PRE_PRICE or UPDATED_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.in=" + DEFAULT_PRE_PRICE + "," + UPDATED_PRE_PRICE);

        // Get all the autoparkList where prePrice equals to UPDATED_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.in=" + UPDATED_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice is not null
        defaultAutoparkShouldBeFound("prePrice.specified=true");

        // Get all the autoparkList where prePrice is null
        defaultAutoparkShouldNotBeFound("prePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice is greater than or equal to DEFAULT_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.greaterThanOrEqual=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice is greater than or equal to UPDATED_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.greaterThanOrEqual=" + UPDATED_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice is less than or equal to DEFAULT_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.lessThanOrEqual=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice is less than or equal to SMALLER_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.lessThanOrEqual=" + SMALLER_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice is less than DEFAULT_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.lessThan=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice is less than UPDATED_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.lessThan=" + UPDATED_PRE_PRICE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPrePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where prePrice is greater than DEFAULT_PRE_PRICE
        defaultAutoparkShouldNotBeFound("prePrice.greaterThan=" + DEFAULT_PRE_PRICE);

        // Get all the autoparkList where prePrice is greater than SMALLER_PRE_PRICE
        defaultAutoparkShouldBeFound("prePrice.greaterThan=" + SMALLER_PRE_PRICE);
    }


    @Test
    @Transactional
    public void getAllAutoparksByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color equals to DEFAULT_COLOR
        defaultAutoparkShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the autoparkList where color equals to UPDATED_COLOR
        defaultAutoparkShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllAutoparksByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color not equals to DEFAULT_COLOR
        defaultAutoparkShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the autoparkList where color not equals to UPDATED_COLOR
        defaultAutoparkShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllAutoparksByColorIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultAutoparkShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the autoparkList where color equals to UPDATED_COLOR
        defaultAutoparkShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllAutoparksByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color is not null
        defaultAutoparkShouldBeFound("color.specified=true");

        // Get all the autoparkList where color is null
        defaultAutoparkShouldNotBeFound("color.specified=false");
    }
                @Test
    @Transactional
    public void getAllAutoparksByColorContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color contains DEFAULT_COLOR
        defaultAutoparkShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the autoparkList where color contains UPDATED_COLOR
        defaultAutoparkShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllAutoparksByColorNotContainsSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where color does not contain DEFAULT_COLOR
        defaultAutoparkShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the autoparkList where color does not contain UPDATED_COLOR
        defaultAutoparkShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }


    @Test
    @Transactional
    public void getAllAutoparksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where type equals to DEFAULT_TYPE
        defaultAutoparkShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the autoparkList where type equals to UPDATED_TYPE
        defaultAutoparkShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where type not equals to DEFAULT_TYPE
        defaultAutoparkShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the autoparkList where type not equals to UPDATED_TYPE
        defaultAutoparkShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAutoparkShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the autoparkList where type equals to UPDATED_TYPE
        defaultAutoparkShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where type is not null
        defaultAutoparkShouldBeFound("type.specified=true");

        // Get all the autoparkList where type is null
        defaultAutoparkShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge equals to DEFAULT_PLEDGE
        defaultAutoparkShouldBeFound("pledge.equals=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge equals to UPDATED_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.equals=" + UPDATED_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge not equals to DEFAULT_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.notEquals=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge not equals to UPDATED_PLEDGE
        defaultAutoparkShouldBeFound("pledge.notEquals=" + UPDATED_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge in DEFAULT_PLEDGE or UPDATED_PLEDGE
        defaultAutoparkShouldBeFound("pledge.in=" + DEFAULT_PLEDGE + "," + UPDATED_PLEDGE);

        // Get all the autoparkList where pledge equals to UPDATED_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.in=" + UPDATED_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge is not null
        defaultAutoparkShouldBeFound("pledge.specified=true");

        // Get all the autoparkList where pledge is null
        defaultAutoparkShouldNotBeFound("pledge.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge is greater than or equal to DEFAULT_PLEDGE
        defaultAutoparkShouldBeFound("pledge.greaterThanOrEqual=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge is greater than or equal to UPDATED_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.greaterThanOrEqual=" + UPDATED_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge is less than or equal to DEFAULT_PLEDGE
        defaultAutoparkShouldBeFound("pledge.lessThanOrEqual=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge is less than or equal to SMALLER_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.lessThanOrEqual=" + SMALLER_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsLessThanSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge is less than DEFAULT_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.lessThan=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge is less than UPDATED_PLEDGE
        defaultAutoparkShouldBeFound("pledge.lessThan=" + UPDATED_PLEDGE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByPledgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where pledge is greater than DEFAULT_PLEDGE
        defaultAutoparkShouldNotBeFound("pledge.greaterThan=" + DEFAULT_PLEDGE);

        // Get all the autoparkList where pledge is greater than SMALLER_PLEDGE
        defaultAutoparkShouldBeFound("pledge.greaterThan=" + SMALLER_PLEDGE);
    }


    @Test
    @Transactional
    public void getAllAutoparksByStatusAvailebleIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where statusAvaileble equals to DEFAULT_STATUS_AVAILEBLE
        defaultAutoparkShouldBeFound("statusAvaileble.equals=" + DEFAULT_STATUS_AVAILEBLE);

        // Get all the autoparkList where statusAvaileble equals to UPDATED_STATUS_AVAILEBLE
        defaultAutoparkShouldNotBeFound("statusAvaileble.equals=" + UPDATED_STATUS_AVAILEBLE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByStatusAvailebleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where statusAvaileble not equals to DEFAULT_STATUS_AVAILEBLE
        defaultAutoparkShouldNotBeFound("statusAvaileble.notEquals=" + DEFAULT_STATUS_AVAILEBLE);

        // Get all the autoparkList where statusAvaileble not equals to UPDATED_STATUS_AVAILEBLE
        defaultAutoparkShouldBeFound("statusAvaileble.notEquals=" + UPDATED_STATUS_AVAILEBLE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByStatusAvailebleIsInShouldWork() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where statusAvaileble in DEFAULT_STATUS_AVAILEBLE or UPDATED_STATUS_AVAILEBLE
        defaultAutoparkShouldBeFound("statusAvaileble.in=" + DEFAULT_STATUS_AVAILEBLE + "," + UPDATED_STATUS_AVAILEBLE);

        // Get all the autoparkList where statusAvaileble equals to UPDATED_STATUS_AVAILEBLE
        defaultAutoparkShouldNotBeFound("statusAvaileble.in=" + UPDATED_STATUS_AVAILEBLE);
    }

    @Test
    @Transactional
    public void getAllAutoparksByStatusAvailebleIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);

        // Get all the autoparkList where statusAvaileble is not null
        defaultAutoparkShouldBeFound("statusAvaileble.specified=true");

        // Get all the autoparkList where statusAvaileble is null
        defaultAutoparkShouldNotBeFound("statusAvaileble.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutoparksByRatesIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);
        Rate rates = RateResourceIT.createEntity(em);
        em.persist(rates);
        em.flush();
        autopark.addRates(rates);
        autoparkRepository.saveAndFlush(autopark);
        Long ratesId = rates.getId();

        // Get all the autoparkList where rates equals to ratesId
        defaultAutoparkShouldBeFound("ratesId.equals=" + ratesId);

        // Get all the autoparkList where rates equals to ratesId + 1
        defaultAutoparkShouldNotBeFound("ratesId.equals=" + (ratesId + 1));
    }


    @Test
    @Transactional
    public void getAllAutoparksByFixcarsIsEqualToSomething() throws Exception {
        // Initialize the database
        autoparkRepository.saveAndFlush(autopark);
        Fixcar fixcars = FixcarResourceIT.createEntity(em);
        em.persist(fixcars);
        em.flush();
        autopark.addFixcars(fixcars);
        autoparkRepository.saveAndFlush(autopark);
        Long fixcarsId = fixcars.getId();

        // Get all the autoparkList where fixcars equals to fixcarsId
        defaultAutoparkShouldBeFound("fixcarsId.equals=" + fixcarsId);

        // Get all the autoparkList where fixcars equals to fixcarsId + 1
        defaultAutoparkShouldNotBeFound("fixcarsId.equals=" + (fixcarsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutoparkShouldBeFound(String filter) throws Exception {
        restAutoparkMockMvc.perform(get("/api/autoparks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autopark.getId().intValue())))
            .andExpect(jsonPath("$.[*].mark").value(hasItem(DEFAULT_MARK)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].prePrice").value(hasItem(DEFAULT_PRE_PRICE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pledge").value(hasItem(DEFAULT_PLEDGE)))
            .andExpect(jsonPath("$.[*].statusAvaileble").value(hasItem(DEFAULT_STATUS_AVAILEBLE.booleanValue())));

        // Check, that the count call also returns 1
        restAutoparkMockMvc.perform(get("/api/autoparks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutoparkShouldNotBeFound(String filter) throws Exception {
        restAutoparkMockMvc.perform(get("/api/autoparks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutoparkMockMvc.perform(get("/api/autoparks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAutopark() throws Exception {
        // Get the autopark
        restAutoparkMockMvc.perform(get("/api/autoparks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutopark() throws Exception {
        // Initialize the database
        autoparkService.save(autopark);

        int databaseSizeBeforeUpdate = autoparkRepository.findAll().size();

        // Update the autopark
        Autopark updatedAutopark = autoparkRepository.findById(autopark.getId()).get();
        // Disconnect from session so that the updates on updatedAutopark are not directly saved in db
        em.detach(updatedAutopark);
        updatedAutopark
            .mark(UPDATED_MARK)
            .model(UPDATED_MODEL)
            .prePrice(UPDATED_PRE_PRICE)
            .color(UPDATED_COLOR)
            .type(UPDATED_TYPE)
            .pledge(UPDATED_PLEDGE)
            .statusAvaileble(UPDATED_STATUS_AVAILEBLE);

        restAutoparkMockMvc.perform(put("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAutopark)))
            .andExpect(status().isOk());

        // Validate the Autopark in the database
        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeUpdate);
        Autopark testAutopark = autoparkList.get(autoparkList.size() - 1);
        assertThat(testAutopark.getMark()).isEqualTo(UPDATED_MARK);
        assertThat(testAutopark.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAutopark.getPrePrice()).isEqualTo(UPDATED_PRE_PRICE);
        assertThat(testAutopark.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testAutopark.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAutopark.getPledge()).isEqualTo(UPDATED_PLEDGE);
        assertThat(testAutopark.isStatusAvaileble()).isEqualTo(UPDATED_STATUS_AVAILEBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutopark() throws Exception {
        int databaseSizeBeforeUpdate = autoparkRepository.findAll().size();

        // Create the Autopark

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoparkMockMvc.perform(put("/api/autoparks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autopark)))
            .andExpect(status().isBadRequest());

        // Validate the Autopark in the database
        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutopark() throws Exception {
        // Initialize the database
        autoparkService.save(autopark);

        int databaseSizeBeforeDelete = autoparkRepository.findAll().size();

        // Delete the autopark
        restAutoparkMockMvc.perform(delete("/api/autoparks/{id}", autopark.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Autopark> autoparkList = autoparkRepository.findAll();
        assertThat(autoparkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
