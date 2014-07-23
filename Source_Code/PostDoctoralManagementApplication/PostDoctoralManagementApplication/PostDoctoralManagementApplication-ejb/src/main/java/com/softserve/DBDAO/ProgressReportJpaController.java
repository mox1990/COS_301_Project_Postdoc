/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ProgressReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ProgressReportJpaController implements Serializable {

    public ProgressReportJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProgressReport progressReport) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application applicationID = progressReport.getApplicationID();
            if (applicationID != null) {
                applicationID = em.getReference(applicationID.getClass(), applicationID.getApplicationID());
                progressReport.setApplicationID(applicationID);
            }
            em.persist(progressReport);
            if (applicationID != null) {
                applicationID.getProgressReportList().add(progressReport);
                applicationID = em.merge(applicationID);
            }
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

    public void edit(ProgressReport progressReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProgressReport persistentProgressReport = em.find(ProgressReport.class, progressReport.getReportID());
            Application applicationIDOld = persistentProgressReport.getApplicationID();
            Application applicationIDNew = progressReport.getApplicationID();
            if (applicationIDNew != null) {
                applicationIDNew = em.getReference(applicationIDNew.getClass(), applicationIDNew.getApplicationID());
                progressReport.setApplicationID(applicationIDNew);
            }
            progressReport = em.merge(progressReport);
            if (applicationIDOld != null && !applicationIDOld.equals(applicationIDNew)) {
                applicationIDOld.getProgressReportList().remove(progressReport);
                applicationIDOld = em.merge(applicationIDOld);
            }
            if (applicationIDNew != null && !applicationIDNew.equals(applicationIDOld)) {
                applicationIDNew.getProgressReportList().add(progressReport);
                applicationIDNew = em.merge(applicationIDNew);
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
                Long id = progressReport.getReportID();
                if (findProgressReport(id) == null) {
                    throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.");
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
            ProgressReport progressReport;
            try {
                progressReport = em.getReference(ProgressReport.class, id);
                progressReport.getReportID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.", enfe);
            }
            Application applicationID = progressReport.getApplicationID();
            if (applicationID != null) {
                applicationID.getProgressReportList().remove(progressReport);
                applicationID = em.merge(applicationID);
            }
            em.remove(progressReport);
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

    public List<ProgressReport> findProgressReportEntities() {
        return findProgressReportEntities(true, -1, -1);
    }

    public List<ProgressReport> findProgressReportEntities(int maxResults, int firstResult) {
        return findProgressReportEntities(false, maxResults, firstResult);
    }

    private List<ProgressReport> findProgressReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgressReport.class));
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

    public ProgressReport findProgressReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgressReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgressReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgressReport> rt = cq.from(ProgressReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
