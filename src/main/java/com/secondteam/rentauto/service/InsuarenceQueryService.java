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

import com.secondteam.rentauto.domain.Insuarence;
import com.secondteam.rentauto.domain.*; // for static metamodels
import com.secondteam.rentauto.repository.InsuarenceRepository;
import com.secondteam.rentauto.service.dto.InsuarenceCriteria;

/**
 * Service for executing complex queries for {@link Insuarence} entities in the database.
 * The main input is a {@link InsuarenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Insuarence} or a {@link Page} of {@link Insuarence} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InsuarenceQueryService extends QueryService<Insuarence> {

    private final Logger log = LoggerFactory.getLogger(InsuarenceQueryService.class);

    private final InsuarenceRepository insuarenceRepository;

    public InsuarenceQueryService(InsuarenceRepository insuarenceRepository) {
        this.insuarenceRepository = insuarenceRepository;
    }

    /**
     * Return a {@link List} of {@link Insuarence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Insuarence> findByCriteria(InsuarenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Insuarence> specification = createSpecification(criteria);
        return insuarenceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Insuarence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Insuarence> findByCriteria(InsuarenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Insuarence> specification = createSpecification(criteria);
        return insuarenceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InsuarenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Insuarence> specification = createSpecification(criteria);
        return insuarenceRepository.count(specification);
    }

    /**
     * Function to convert {@link InsuarenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Insuarence> createSpecification(InsuarenceCriteria criteria) {
        Specification<Insuarence> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Insuarence_.id));
            }
            if (criteria.getDateApply() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApply(), Insuarence_.dateApply));
            }
            if (criteria.getDateEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEnd(), Insuarence_.dateEnd));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Insuarence_.cost));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Insuarence_.type));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Insuarence_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
