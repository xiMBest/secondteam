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

import com.secondteam.rentauto.domain.Penalty;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.PenaltyRepository;
import com.secondteam.rentauto.service.dto.PenaltyCriteria;

/**
 * Service for executing complex queries for {@link Penalty} entities in the database.
 * The main input is a {@link PenaltyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Penalty} or a {@link Page} of {@link Penalty} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PenaltyQueryService extends QueryService<Penalty> {

    private final Logger log = LoggerFactory.getLogger(PenaltyQueryService.class);

    private final PenaltyRepository penaltyRepository;

    public PenaltyQueryService(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    /**
     * Return a {@link List} of {@link Penalty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Penalty> findByCriteria(PenaltyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Penalty> specification = createSpecification(criteria);
        return penaltyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Penalty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Penalty> findByCriteria(PenaltyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Penalty> specification = createSpecification(criteria);
        return penaltyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PenaltyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Penalty> specification = createSpecification(criteria);
        return penaltyRepository.count(specification);
    }

    /**
     * Function to convert {@link PenaltyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Penalty> createSpecification(PenaltyCriteria criteria) {
        Specification<Penalty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Penalty_.id));
            }
            if (criteria.getDatePenalty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePenalty(), Penalty_.datePenalty));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Penalty_.price));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Penalty_.description));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Penalty_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
