package com.secondteam.rentauto.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.secondteam.rentauto.domain.Customer;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.CustomerRepository;
import com.secondteam.rentauto.service.dto.CustomerCriteria;

/**
 * Service for executing complex queries for {@link Customer} entities in the database.
 * The main input is a {@link CustomerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Customer} or a {@link Page} of {@link Customer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Return a {@link List} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customer> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customer> createSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customer_.name));
            }
            if (criteria.getSurname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurname(), Customer_.surname));
            }
            if (criteria.getYears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYears(), Customer_.years));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Customer_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Customer_.phone));
            }
            if (criteria.getAdress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdress(), Customer_.adress));
            }
            if (criteria.getReservecarsId() != null) {
                specification = specification.and(buildSpecification(criteria.getReservecarsId(),
                    root -> root.join(Customer_.reservecars, JoinType.LEFT).get(Reservecar_.id)));
            }
            if (criteria.getPenaltysId() != null) {
                specification = specification.and(buildSpecification(criteria.getPenaltysId(),
                    root -> root.join(Customer_.penaltys, JoinType.LEFT).get(Penalty_.id)));
            }
            if (criteria.getInsuarencesId() != null) {
                specification = specification.and(buildSpecification(criteria.getInsuarencesId(),
                    root -> root.join(Customer_.insuarences, JoinType.LEFT).get(Insuarence_.id)));
            }
        }
        return specification;
    }
}
