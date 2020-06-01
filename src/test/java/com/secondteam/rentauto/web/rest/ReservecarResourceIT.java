package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Reservecar;
import com.secondteam.rentauto.domain.Customer;
import com.secondteam.rentauto.repository.ReservecarRepository;
import com.secondteam.rentauto.service.ReservecarService;
import com.secondteam.rentauto.service.dto.ReservecarCriteria;
import com.secondteam.rentauto.service.ReservecarQueryService;

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

import com.secondteam.rentauto.domain.enumeration.CarType;
/**
 * Integration tests for the {@link ReservecarResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReservecarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CarType DEFAULT_TYPE = CarType.Car;
    private static final CarType UPDATED_TYPE = CarType.SUV;

    private static final Integer DEFAULT_TOTAL_PRICE = 1;
    private static final Integer UPDATED_TOTAL_PRICE = 2;
    private static final Integer SMALLER_TOTAL_PRICE = 1 - 1;

    private static final ZonedDateTime DEFAULT_DATE_PICK_UP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_PICK_UP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_PICK_UP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_DROP_DOWN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DROP_DOWN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_DROP_DOWN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReservecarRepository reservecarRepository;

    @Autowired
    private ReservecarService reservecarService;

    @Autowired
    private ReservecarQueryService reservecarQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservecarMockMvc;

    private Reservecar reservecar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservecar createEntity(EntityManager em) {
        Reservecar reservecar = new Reservecar()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .datePickUP(DEFAULT_DATE_PICK_UP)
            .dateDropDown(DEFAULT_DATE_DROP_DOWN)
            .description(DEFAULT_DESCRIPTION);
        return reservecar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservecar createUpdatedEntity(EntityManager em) {
        Reservecar reservecar = new Reservecar()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .datePickUP(UPDATED_DATE_PICK_UP)
            .dateDropDown(UPDATED_DATE_DROP_DOWN)
            .description(UPDATED_DESCRIPTION);
        return reservecar;
    }

    @BeforeEach
    public void initTest() {
        reservecar = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservecar() throws Exception {
        int databaseSizeBeforeCreate = reservecarRepository.findAll().size();

        // Create the Reservecar
        restReservecarMockMvc.perform(post("/api/reservecars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservecar)))
            .andExpect(status().isCreated());

        // Validate the Reservecar in the database
        List<Reservecar> reservecarList = reservecarRepository.findAll();
        assertThat(reservecarList).hasSize(databaseSizeBeforeCreate + 1);
        Reservecar testReservecar = reservecarList.get(reservecarList.size() - 1);
        assertThat(testReservecar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReservecar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReservecar.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testReservecar.getDatePickUP()).isEqualTo(DEFAULT_DATE_PICK_UP);
        assertThat(testReservecar.getDateDropDown()).isEqualTo(DEFAULT_DATE_DROP_DOWN);
        assertThat(testReservecar.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReservecarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservecarRepository.findAll().size();

        // Create the Reservecar with an existing ID
        reservecar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservecarMockMvc.perform(post("/api/reservecars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservecar)))
            .andExpect(status().isBadRequest());

        // Validate the Reservecar in the database
        List<Reservecar> reservecarList = reservecarRepository.findAll();
        assertThat(reservecarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservecars() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList
        restReservecarMockMvc.perform(get("/api/reservecars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservecar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.[*].datePickUP").value(hasItem(sameInstant(DEFAULT_DATE_PICK_UP))))
            .andExpect(jsonPath("$.[*].dateDropDown").value(hasItem(sameInstant(DEFAULT_DATE_DROP_DOWN))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getReservecar() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get the reservecar
        restReservecarMockMvc.perform(get("/api/reservecars/{id}", reservecar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservecar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE))
            .andExpect(jsonPath("$.datePickUP").value(sameInstant(DEFAULT_DATE_PICK_UP)))
            .andExpect(jsonPath("$.dateDropDown").value(sameInstant(DEFAULT_DATE_DROP_DOWN)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getReservecarsByIdFiltering() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        Long id = reservecar.getId();

        defaultReservecarShouldBeFound("id.equals=" + id);
        defaultReservecarShouldNotBeFound("id.notEquals=" + id);

        defaultReservecarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReservecarShouldNotBeFound("id.greaterThan=" + id);

        defaultReservecarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReservecarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReservecarsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name equals to DEFAULT_NAME
        defaultReservecarShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reservecarList where name equals to UPDATED_NAME
        defaultReservecarShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReservecarsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name not equals to DEFAULT_NAME
        defaultReservecarShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the reservecarList where name not equals to UPDATED_NAME
        defaultReservecarShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReservecarsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReservecarShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reservecarList where name equals to UPDATED_NAME
        defaultReservecarShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReservecarsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name is not null
        defaultReservecarShouldBeFound("name.specified=true");

        // Get all the reservecarList where name is null
        defaultReservecarShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReservecarsByNameContainsSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name contains DEFAULT_NAME
        defaultReservecarShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the reservecarList where name contains UPDATED_NAME
        defaultReservecarShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReservecarsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where name does not contain DEFAULT_NAME
        defaultReservecarShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the reservecarList where name does not contain UPDATED_NAME
        defaultReservecarShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReservecarsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where type equals to DEFAULT_TYPE
        defaultReservecarShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the reservecarList where type equals to UPDATED_TYPE
        defaultReservecarShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where type not equals to DEFAULT_TYPE
        defaultReservecarShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the reservecarList where type not equals to UPDATED_TYPE
        defaultReservecarShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultReservecarShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the reservecarList where type equals to UPDATED_TYPE
        defaultReservecarShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where type is not null
        defaultReservecarShouldBeFound("type.specified=true");

        // Get all the reservecarList where type is null
        defaultReservecarShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice is not null
        defaultReservecarShouldBeFound("totalPrice.specified=true");

        // Get all the reservecarList where totalPrice is null
        defaultReservecarShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllReservecarsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultReservecarShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the reservecarList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultReservecarShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP equals to DEFAULT_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.equals=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP equals to UPDATED_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.equals=" + UPDATED_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP not equals to DEFAULT_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.notEquals=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP not equals to UPDATED_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.notEquals=" + UPDATED_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP in DEFAULT_DATE_PICK_UP or UPDATED_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.in=" + DEFAULT_DATE_PICK_UP + "," + UPDATED_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP equals to UPDATED_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.in=" + UPDATED_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP is not null
        defaultReservecarShouldBeFound("datePickUP.specified=true");

        // Get all the reservecarList where datePickUP is null
        defaultReservecarShouldNotBeFound("datePickUP.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP is greater than or equal to DEFAULT_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.greaterThanOrEqual=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP is greater than or equal to UPDATED_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.greaterThanOrEqual=" + UPDATED_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP is less than or equal to DEFAULT_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.lessThanOrEqual=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP is less than or equal to SMALLER_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.lessThanOrEqual=" + SMALLER_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsLessThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP is less than DEFAULT_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.lessThan=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP is less than UPDATED_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.lessThan=" + UPDATED_DATE_PICK_UP);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDatePickUPIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where datePickUP is greater than DEFAULT_DATE_PICK_UP
        defaultReservecarShouldNotBeFound("datePickUP.greaterThan=" + DEFAULT_DATE_PICK_UP);

        // Get all the reservecarList where datePickUP is greater than SMALLER_DATE_PICK_UP
        defaultReservecarShouldBeFound("datePickUP.greaterThan=" + SMALLER_DATE_PICK_UP);
    }


    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown equals to DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.equals=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown equals to UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.equals=" + UPDATED_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown not equals to DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.notEquals=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown not equals to UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.notEquals=" + UPDATED_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown in DEFAULT_DATE_DROP_DOWN or UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.in=" + DEFAULT_DATE_DROP_DOWN + "," + UPDATED_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown equals to UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.in=" + UPDATED_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown is not null
        defaultReservecarShouldBeFound("dateDropDown.specified=true");

        // Get all the reservecarList where dateDropDown is null
        defaultReservecarShouldNotBeFound("dateDropDown.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown is greater than or equal to DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.greaterThanOrEqual=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown is greater than or equal to UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.greaterThanOrEqual=" + UPDATED_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown is less than or equal to DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.lessThanOrEqual=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown is less than or equal to SMALLER_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.lessThanOrEqual=" + SMALLER_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsLessThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown is less than DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.lessThan=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown is less than UPDATED_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.lessThan=" + UPDATED_DATE_DROP_DOWN);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDateDropDownIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where dateDropDown is greater than DEFAULT_DATE_DROP_DOWN
        defaultReservecarShouldNotBeFound("dateDropDown.greaterThan=" + DEFAULT_DATE_DROP_DOWN);

        // Get all the reservecarList where dateDropDown is greater than SMALLER_DATE_DROP_DOWN
        defaultReservecarShouldBeFound("dateDropDown.greaterThan=" + SMALLER_DATE_DROP_DOWN);
    }


    @Test
    @Transactional
    public void getAllReservecarsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description equals to DEFAULT_DESCRIPTION
        defaultReservecarShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reservecarList where description equals to UPDATED_DESCRIPTION
        defaultReservecarShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description not equals to DEFAULT_DESCRIPTION
        defaultReservecarShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the reservecarList where description not equals to UPDATED_DESCRIPTION
        defaultReservecarShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReservecarShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reservecarList where description equals to UPDATED_DESCRIPTION
        defaultReservecarShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description is not null
        defaultReservecarShouldBeFound("description.specified=true");

        // Get all the reservecarList where description is null
        defaultReservecarShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllReservecarsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description contains DEFAULT_DESCRIPTION
        defaultReservecarShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the reservecarList where description contains UPDATED_DESCRIPTION
        defaultReservecarShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReservecarsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);

        // Get all the reservecarList where description does not contain DEFAULT_DESCRIPTION
        defaultReservecarShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the reservecarList where description does not contain UPDATED_DESCRIPTION
        defaultReservecarShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllReservecarsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        reservecarRepository.saveAndFlush(reservecar);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        reservecar.setCustomer(customer);
        reservecarRepository.saveAndFlush(reservecar);
        Long customerId = customer.getId();

        // Get all the reservecarList where customer equals to customerId
        defaultReservecarShouldBeFound("customerId.equals=" + customerId);

        // Get all the reservecarList where customer equals to customerId + 1
        defaultReservecarShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReservecarShouldBeFound(String filter) throws Exception {
        restReservecarMockMvc.perform(get("/api/reservecars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservecar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.[*].datePickUP").value(hasItem(sameInstant(DEFAULT_DATE_PICK_UP))))
            .andExpect(jsonPath("$.[*].dateDropDown").value(hasItem(sameInstant(DEFAULT_DATE_DROP_DOWN))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restReservecarMockMvc.perform(get("/api/reservecars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReservecarShouldNotBeFound(String filter) throws Exception {
        restReservecarMockMvc.perform(get("/api/reservecars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReservecarMockMvc.perform(get("/api/reservecars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReservecar() throws Exception {
        // Get the reservecar
        restReservecarMockMvc.perform(get("/api/reservecars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservecar() throws Exception {
        // Initialize the database
        reservecarService.save(reservecar);

        int databaseSizeBeforeUpdate = reservecarRepository.findAll().size();

        // Update the reservecar
        Reservecar updatedReservecar = reservecarRepository.findById(reservecar.getId()).get();
        // Disconnect from session so that the updates on updatedReservecar are not directly saved in db
        em.detach(updatedReservecar);
        updatedReservecar
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .datePickUP(UPDATED_DATE_PICK_UP)
            .dateDropDown(UPDATED_DATE_DROP_DOWN)
            .description(UPDATED_DESCRIPTION);

        restReservecarMockMvc.perform(put("/api/reservecars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservecar)))
            .andExpect(status().isOk());

        // Validate the Reservecar in the database
        List<Reservecar> reservecarList = reservecarRepository.findAll();
        assertThat(reservecarList).hasSize(databaseSizeBeforeUpdate);
        Reservecar testReservecar = reservecarList.get(reservecarList.size() - 1);
        assertThat(testReservecar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReservecar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReservecar.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testReservecar.getDatePickUP()).isEqualTo(UPDATED_DATE_PICK_UP);
        assertThat(testReservecar.getDateDropDown()).isEqualTo(UPDATED_DATE_DROP_DOWN);
        assertThat(testReservecar.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReservecar() throws Exception {
        int databaseSizeBeforeUpdate = reservecarRepository.findAll().size();

        // Create the Reservecar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservecarMockMvc.perform(put("/api/reservecars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservecar)))
            .andExpect(status().isBadRequest());

        // Validate the Reservecar in the database
        List<Reservecar> reservecarList = reservecarRepository.findAll();
        assertThat(reservecarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservecar() throws Exception {
        // Initialize the database
        reservecarService.save(reservecar);

        int databaseSizeBeforeDelete = reservecarRepository.findAll().size();

        // Delete the reservecar
        restReservecarMockMvc.perform(delete("/api/reservecars/{id}", reservecar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservecar> reservecarList = reservecarRepository.findAll();
        assertThat(reservecarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
