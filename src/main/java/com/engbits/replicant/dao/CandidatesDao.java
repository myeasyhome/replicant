package com.engbits.replicant.dao;

import com.engbits.replicant.model.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

/**
 * DAO to handle the persistence of {@link com.engbits.replicant.model.Candidate} entities
 *
 * @author replicant.team
 * @since 0.1.0
 */
@Repository
public class CandidatesDao {

    private static final Logger LOG = LoggerFactory.getLogger(CandidatesDao.class);

    private EntityManager entityManager;

    /**
     * Inserts the given {@link Candidate} for persistence
     * @param candidate {@link Candidate} to persist
     */
    public void insert(final Candidate candidate) {
        candidate.setCreateDate(Date.from(Instant.now()));
        candidate.setIsActive(true);

        LOG.debug("Inserting Candidate: {}", candidate);
        entityManager.persist(candidate);
    }

    /**
     * Selects all the {@link Candidate} entities that are in persistence
     * @return {@link List} of all {@link Candidate} entities in persistence
     */
    public List<Candidate> selectAll() {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        final CriteriaQuery<Candidate> criteriaQuery = criteriaBuilder.createQuery(Candidate.class);
        final Root<Candidate> queryRoot = criteriaQuery.from(Candidate.class);
        criteriaQuery.select(queryRoot);

        criteriaQuery.orderBy(
                criteriaBuilder.asc(
                        queryRoot.get("lastName")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Selects the specific Candidate with the given ID
     * @param candidateId ID of the Candidate to select
     * @return {@link Candidate} record with the given ID or null if not found
     */
    public Candidate selectById(final Long candidateId) {
        return entityManager.find(Candidate.class, candidateId);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
