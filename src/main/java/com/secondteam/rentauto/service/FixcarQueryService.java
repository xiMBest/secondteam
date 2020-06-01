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

import com.secondteam.rentauto.domain.Fixcar;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.FixcarRepository;
import com.secondteam.rentauto.service.dto.FixcarCriteria;

/**
 * Service for executing complex queries for {@link Fixcar} entities in the database.
 * The main input is a {@link FixcarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fixcar} or a {@link Page} of {@link Fixcar} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixcarQueryService extends QueryService<Fixcar> {

    private final Logger log = LoggerFactory.getLogger(FixcarQueryService.class);

    private final FixcarRepository fixcarRepository;

    public FixcarQueryService(FixcarRepository fixcarRepository) {
        this.fixcarRepository = fixcarRepository;
    }

    /**
     * Return a {@link List} of {@link Fixcar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fixcar> findByCriteria(FixcarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fixcar> specification = createSpecification(criteria);
        return fixcarRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fixcar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fixcar> findByCriteria(FixcarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fixcar> specification = createSpecification(criteria);
        return fixcarRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixcarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fixcar> specification = createSpecification(criteria);
        return fixcarRepository.count(specification);
    }

    /**
     * Function to convert {@link FixcarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fixcar> createSpecification(FixcarCriteria criteria) {
        Specification<Fixcar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fixcar_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Fixcar_.price));
            }
            if (criteria.getDateFixing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFixing(), Fixcar_.dateFixing));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Fixcar_.description));
            }
            if (criteria.getAutoparksId() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoparksId(),
                    root -> root.join(Fixcar_.autoparks, JoinType.LEFT).get(Autopark_.id)));
            }
        }
        return specification;
    }
}
