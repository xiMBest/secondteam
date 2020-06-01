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

import com.secondteam.rentauto.domain.Reservecar;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.ReservecarRepository;
import com.secondteam.rentauto.service.dto.ReservecarCriteria;

/**
 * Service for executing complex queries for {@link Reservecar} entities in the database.
 * The main input is a {@link ReservecarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Reservecar} or a {@link Page} of {@link Reservecar} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReservecarQueryService extends QueryService<Reservecar> {

    private final Logger log = LoggerFactory.getLogger(ReservecarQueryService.class);

    private final ReservecarRepository reservecarRepository;

    public ReservecarQueryService(ReservecarRepository reservecarRepository) {
        this.reservecarRepository = reservecarRepository;
    }

    /**
     * Return a {@link List} of {@link Reservecar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Reservecar> findByCriteria(ReservecarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reservecar> specification = createSpecification(criteria);
        return reservecarRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Reservecar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Reservecar> findByCriteria(ReservecarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reservecar> specification = createSpecification(criteria);
        return reservecarRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReservecarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reservecar> specification = createSpecification(criteria);
        return reservecarRepository.count(specification);
    }

    /**
     * Function to convert {@link ReservecarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reservecar> createSpecification(ReservecarCriteria criteria) {
        Specification<Reservecar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reservecar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Reservecar_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Reservecar_.type));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), Reservecar_.totalPrice));
            }
            if (criteria.getDatePickUP() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePickUP(), Reservecar_.datePickUP));
            }
            if (criteria.getDateDropDown() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDropDown(), Reservecar_.dateDropDown));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Reservecar_.description));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Reservecar_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
