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

import com.secondteam.rentauto.domain.Rate;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.RateRepository;
import com.secondteam.rentauto.service.dto.RateCriteria;

/**
 * Service for executing complex queries for {@link Rate} entities in the database.
 * The main input is a {@link RateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Rate} or a {@link Page} of {@link Rate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RateQueryService extends QueryService<Rate> {

    private final Logger log = LoggerFactory.getLogger(RateQueryService.class);

    private final RateRepository rateRepository;

    public RateQueryService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    /**
     * Return a {@link List} of {@link Rate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Rate> findByCriteria(RateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Rate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Rate> findByCriteria(RateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateRepository.count(specification);
    }

    /**
     * Function to convert {@link RateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rate> createSpecification(RateCriteria criteria) {
        Specification<Rate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rate_.id));
            }
            if (criteria.getRaiting() != null) {
                specification = specification.and(buildSpecification(criteria.getRaiting(), Rate_.raiting));
            }
            if (criteria.getAutoparkId() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoparkId(),
                    root -> root.join(Rate_.autopark, JoinType.LEFT).get(Autopark_.id)));
            }
        }
        return specification;
    }
}
