/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RecommendationReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class RecommendationReportJpaController implements Serializable {

    public RecommendationReportJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecommendationReport recommendationReport) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person hodID = recommendationReport.getHodID();
            if (hodID != null) {
                hodID = em.getReference(hodID.getClass(), hodID.getSystemID());
                recommendationReport.setHodID(hodID);
            }
            em.persist(recommendationReport);
            if (hodID != null) {
                hodID.getRecommendationReportCollection().add(recommendationReport);
                hodID = em.merge(hodID);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRecommendationReport(recommendationReport.getReportID()) != null) {
                throw new PreexistingEntityException("RecommendationReport " + recommendationReport + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecommendationReport recommendationReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RecommendationReport persistentRecommendationReport = em.find(RecommendationReport.class, recommendationReport.getReportID());
            Person hodIDOld = persistentRecommendationReport.getHodID();
            Person hodIDNew = recommendationReport.getHodID();
            if (hodIDNew != null) {
                hodIDNew = em.getReference(hodIDNew.getClass(), hodIDNew.getSystemID());
                recommendationReport.setHodID(hodIDNew);
            }
            recommendationReport = em.merge(recommendationReport);
            if (hodIDOld != null && !hodIDOld.equals(hodIDNew)) {
                hodIDOld.getRecommendationReportCollection().remove(recommendationReport);
                hodIDOld = em.merge(hodIDOld);
            }
            if (hodIDNew != null && !hodIDNew.equals(hodIDOld)) {
                hodIDNew.getRecommendationReportCollection().add(recommendationReport);
                hodIDNew = em.merge(hodIDNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = recommendationReport.getReportID();
                if (findRecommendationReport(id) == null) {
                    throw new NonexistentEntityException("The recommendationReport with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RecommendationReport recommendationReport;
            try {
                recommendationReport = em.getReference(RecommendationReport.class, id);
                recommendationReport.getReportID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recommendationReport with id " + id + " no longer exists.", enfe);
            }
            Person hodID = recommendationReport.getHodID();
            if (hodID != null) {
                hodID.getRecommendationReportCollection().remove(recommendationReport);
                hodID = em.merge(hodID);
            }
            em.remove(recommendationReport);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecommendationReport> findRecommendationReportEntities() {
        return findRecommendationReportEntities(true, -1, -1);
    }

    public List<RecommendationReport> findRecommendationReportEntities(int maxResults, int firstResult) {
        return findRecommendationReportEntities(false, maxResults, firstResult);
    }

    private List<RecommendationReport> findRecommendationReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecommendationReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RecommendationReport findRecommendationReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecommendationReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecommendationReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecommendationReport> rt = cq.from(RecommendationReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
