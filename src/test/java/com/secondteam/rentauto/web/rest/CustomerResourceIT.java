package com.secondteam.rentauto.web.rest;

import com.secondteam.rentauto.RentautoApp;
import com.secondteam.rentauto.domain.Customer;
import com.secondteam.rentauto.domain.Reservecar;
import com.secondteam.rentauto.domain.Penalty;
import com.secondteam.rentauto.domain.Insuarence;
import com.secondteam.rentauto.repository.CustomerRepository;
import com.secondteam.rentauto.service.CustomerService;
import com.secondteam.rentauto.service.dto.CustomerCriteria;
import com.secondteam.rentauto.service.CustomerQueryService;

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

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = RentautoApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEARS = 1;
    private static final Integer UPDATED_YEARS = 2;
    private static final Integer SMALLER_YEARS = 1 - 1;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "+9657798581931";
    private static final String UPDATED_PHONE = "2299660612";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .years(DEFAULT_YEARS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .adress(DEFAULT_ADRESS);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .years(UPDATED_YEARS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .adress(UPDATED_ADRESS);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testCustomer.getYears()).isEqualTo(DEFAULT_YEARS);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomer.getAdress()).isEqualTo(DEFAULT_ADRESS);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setSurname(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setEmail(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdressIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setAdress(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].years").value(hasItem(DEFAULT_YEARS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.years").value(DEFAULT_YEARS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS));
    }


    @Test
    @Transactional
    public void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        Long id = customer.getId();

        defaultCustomerShouldBeFound("id.equals=" + id);
        defaultCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name equals to DEFAULT_NAME
        defaultCustomerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name not equals to DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the customerList where name not equals to UPDATED_NAME
        defaultCustomerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name is not null
        defaultCustomerShouldBeFound("name.specified=true");

        // Get all the customerList where name is null
        defaultCustomerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name contains DEFAULT_NAME
        defaultCustomerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerList where name contains UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name does not contain DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerList where name does not contain UPDATED_NAME
        defaultCustomerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersBySurnameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname equals to DEFAULT_SURNAME
        defaultCustomerShouldBeFound("surname.equals=" + DEFAULT_SURNAME);

        // Get all the customerList where surname equals to UPDATED_SURNAME
        defaultCustomerShouldNotBeFound("surname.equals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllCustomersBySurnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname not equals to DEFAULT_SURNAME
        defaultCustomerShouldNotBeFound("surname.notEquals=" + DEFAULT_SURNAME);

        // Get all the customerList where surname not equals to UPDATED_SURNAME
        defaultCustomerShouldBeFound("surname.notEquals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllCustomersBySurnameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname in DEFAULT_SURNAME or UPDATED_SURNAME
        defaultCustomerShouldBeFound("surname.in=" + DEFAULT_SURNAME + "," + UPDATED_SURNAME);

        // Get all the customerList where surname equals to UPDATED_SURNAME
        defaultCustomerShouldNotBeFound("surname.in=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllCustomersBySurnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname is not null
        defaultCustomerShouldBeFound("surname.specified=true");

        // Get all the customerList where surname is null
        defaultCustomerShouldNotBeFound("surname.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersBySurnameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname contains DEFAULT_SURNAME
        defaultCustomerShouldBeFound("surname.contains=" + DEFAULT_SURNAME);

        // Get all the customerList where surname contains UPDATED_SURNAME
        defaultCustomerShouldNotBeFound("surname.contains=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void getAllCustomersBySurnameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where surname does not contain DEFAULT_SURNAME
        defaultCustomerShouldNotBeFound("surname.doesNotContain=" + DEFAULT_SURNAME);

        // Get all the customerList where surname does not contain UPDATED_SURNAME
        defaultCustomerShouldBeFound("surname.doesNotContain=" + UPDATED_SURNAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByYearsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years equals to DEFAULT_YEARS
        defaultCustomerShouldBeFound("years.equals=" + DEFAULT_YEARS);

        // Get all the customerList where years equals to UPDATED_YEARS
        defaultCustomerShouldNotBeFound("years.equals=" + UPDATED_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years not equals to DEFAULT_YEARS
        defaultCustomerShouldNotBeFound("years.notEquals=" + DEFAULT_YEARS);

        // Get all the customerList where years not equals to UPDATED_YEARS
        defaultCustomerShouldBeFound("years.notEquals=" + UPDATED_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years in DEFAULT_YEARS or UPDATED_YEARS
        defaultCustomerShouldBeFound("years.in=" + DEFAULT_YEARS + "," + UPDATED_YEARS);

        // Get all the customerList where years equals to UPDATED_YEARS
        defaultCustomerShouldNotBeFound("years.in=" + UPDATED_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years is not null
        defaultCustomerShouldBeFound("years.specified=true");

        // Get all the customerList where years is null
        defaultCustomerShouldNotBeFound("years.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years is greater than or equal to DEFAULT_YEARS
        defaultCustomerShouldBeFound("years.greaterThanOrEqual=" + DEFAULT_YEARS);

        // Get all the customerList where years is greater than or equal to UPDATED_YEARS
        defaultCustomerShouldNotBeFound("years.greaterThanOrEqual=" + UPDATED_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years is less than or equal to DEFAULT_YEARS
        defaultCustomerShouldBeFound("years.lessThanOrEqual=" + DEFAULT_YEARS);

        // Get all the customerList where years is less than or equal to SMALLER_YEARS
        defaultCustomerShouldNotBeFound("years.lessThanOrEqual=" + SMALLER_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years is less than DEFAULT_YEARS
        defaultCustomerShouldNotBeFound("years.lessThan=" + DEFAULT_YEARS);

        // Get all the customerList where years is less than UPDATED_YEARS
        defaultCustomerShouldBeFound("years.lessThan=" + UPDATED_YEARS);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where years is greater than DEFAULT_YEARS
        defaultCustomerShouldNotBeFound("years.greaterThan=" + DEFAULT_YEARS);

        // Get all the customerList where years is greater than SMALLER_YEARS
        defaultCustomerShouldBeFound("years.greaterThan=" + SMALLER_YEARS);
    }


    @Test
    @Transactional
    public void getAllCustomersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email equals to DEFAULT_EMAIL
        defaultCustomerShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the customerList where email equals to UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email not equals to DEFAULT_EMAIL
        defaultCustomerShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the customerList where email not equals to UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the customerList where email equals to UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email is not null
        defaultCustomerShouldBeFound("email.specified=true");

        // Get all the customerList where email is null
        defaultCustomerShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByEmailContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email contains DEFAULT_EMAIL
        defaultCustomerShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the customerList where email contains UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email does not contain DEFAULT_EMAIL
        defaultCustomerShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the customerList where email does not contain UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCustomersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone equals to DEFAULT_PHONE
        defaultCustomerShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the customerList where phone equals to UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone not equals to DEFAULT_PHONE
        defaultCustomerShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the customerList where phone not equals to UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the customerList where phone equals to UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone is not null
        defaultCustomerShouldBeFound("phone.specified=true");

        // Get all the customerList where phone is null
        defaultCustomerShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone contains DEFAULT_PHONE
        defaultCustomerShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the customerList where phone contains UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone does not contain DEFAULT_PHONE
        defaultCustomerShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the customerList where phone does not contain UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCustomersByAdressIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress equals to DEFAULT_ADRESS
        defaultCustomerShouldBeFound("adress.equals=" + DEFAULT_ADRESS);

        // Get all the customerList where adress equals to UPDATED_ADRESS
        defaultCustomerShouldNotBeFound("adress.equals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAdressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress not equals to DEFAULT_ADRESS
        defaultCustomerShouldNotBeFound("adress.notEquals=" + DEFAULT_ADRESS);

        // Get all the customerList where adress not equals to UPDATED_ADRESS
        defaultCustomerShouldBeFound("adress.notEquals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAdressIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress in DEFAULT_ADRESS or UPDATED_ADRESS
        defaultCustomerShouldBeFound("adress.in=" + DEFAULT_ADRESS + "," + UPDATED_ADRESS);

        // Get all the customerList where adress equals to UPDATED_ADRESS
        defaultCustomerShouldNotBeFound("adress.in=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAdressIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress is not null
        defaultCustomerShouldBeFound("adress.specified=true");

        // Get all the customerList where adress is null
        defaultCustomerShouldNotBeFound("adress.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAdressContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress contains DEFAULT_ADRESS
        defaultCustomerShouldBeFound("adress.contains=" + DEFAULT_ADRESS);

        // Get all the customerList where adress contains UPDATED_ADRESS
        defaultCustomerShouldNotBeFound("adress.contains=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAdressNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where adress does not contain DEFAULT_ADRESS
        defaultCustomerShouldNotBeFound("adress.doesNotContain=" + DEFAULT_ADRESS);

        // Get all the customerList where adress does not contain UPDATED_ADRESS
        defaultCustomerShouldBeFound("adress.doesNotContain=" + UPDATED_ADRESS);
    }


    @Test
    @Transactional
    public void getAllCustomersByReservecarsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Reservecar reservecars = ReservecarResourceIT.createEntity(em);
        em.persist(reservecars);
        em.flush();
        customer.addReservecars(reservecars);
        customerRepository.saveAndFlush(customer);
        Long reservecarsId = reservecars.getId();

        // Get all the customerList where reservecars equals to reservecarsId
        defaultCustomerShouldBeFound("reservecarsId.equals=" + reservecarsId);

        // Get all the customerList where reservecars equals to reservecarsId + 1
        defaultCustomerShouldNotBeFound("reservecarsId.equals=" + (reservecarsId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByPenaltysIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Penalty penaltys = PenaltyResourceIT.createEntity(em);
        em.persist(penaltys);
        em.flush();
        customer.addPenaltys(penaltys);
        customerRepository.saveAndFlush(customer);
        Long penaltysId = penaltys.getId();

        // Get all the customerList where penaltys equals to penaltysId
        defaultCustomerShouldBeFound("penaltysId.equals=" + penaltysId);

        // Get all the customerList where penaltys equals to penaltysId + 1
        defaultCustomerShouldNotBeFound("penaltysId.equals=" + (penaltysId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByInsuarencesIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Insuarence insuarences = InsuarenceResourceIT.createEntity(em);
        em.persist(insuarences);
        em.flush();
        customer.addInsuarences(insuarences);
        customerRepository.saveAndFlush(customer);
        Long insuarencesId = insuarences.getId();

        // Get all the customerList where insuarences equals to insuarencesId
        defaultCustomerShouldBeFound("insuarencesId.equals=" + insuarencesId);

        // Get all the customerList where insuarences equals to insuarencesId + 1
        defaultCustomerShouldNotBeFound("insuarencesId.equals=" + (insuarencesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].years").value(hasItem(DEFAULT_YEARS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)));

        // Check, that the count call also returns 1
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .years(UPDATED_YEARS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .adress(UPDATED_ADRESS);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testCustomer.getYears()).isEqualTo(UPDATED_YEARS);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomer.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
