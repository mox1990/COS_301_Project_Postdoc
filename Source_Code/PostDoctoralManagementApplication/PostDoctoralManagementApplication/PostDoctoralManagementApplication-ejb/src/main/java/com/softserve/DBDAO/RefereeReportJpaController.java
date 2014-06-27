/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RefereeReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class RefereeReportJpaController implements Serializable {

    public RefereeReportJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RefereeReport refereeReport) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application applicationID = refereeReport.getApplicationID();
            if (applicationID != null) {
                applicationID = em.getReference(applicationID.getClass(), applicationID.getApplicationID());
                refereeReport.setApplicationID(applicationID);
            }
            Person refereeID = refereeReport.getRefereeID();
            if (refereeID != null) {
                refereeID = em.getReference(refereeID.getClass(), refereeID.getSystemID());
                refereeReport.setRefereeID(refereeID);
            }
            em.persist(refereeReport);
            if (applicationID != null) {
                applicationID.getRefereeReportList().add(refereeReport);
                applicationID = em.merge(applicationID);
            }
            if (refereeID != null) {
                refereeID.getRefereeReportList().add(refereeReport);
                refereeID = em.merge(refereeID);
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

    public void edit(RefereeReport refereeReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RefereeReport persistentRefereeReport = em.find(RefereeReport.class, refereeReport.getReportID());
            Application applicationIDOld = persistentRefereeReport.getApplicationID();
            Application applicationIDNew = refereeReport.getApplicationID();
            Person refereeIDOld = persistentRefereeReport.getRefereeID();
            Person refereeIDNew = refereeReport.getRefereeID();
            if (applicationIDNew != null) {
                applicationIDNew = em.getReference(applicationIDNew.getClass(), applicationIDNew.getApplicationID());
                refereeReport.setApplicationID(applicationIDNew);
            }
            if (refereeIDNew != null) {
                refereeIDNew = em.getReference(refereeIDNew.getClass(), refereeIDNew.getSystemID());
                refereeReport.setRefereeID(refereeIDNew);
            }
            refereeReport = em.merge(refereeReport);
            if (applicationIDOld != null && !applicationIDOld.equals(applicationIDNew)) {
                applicationIDOld.getRefereeReportList().remove(refereeReport);
                applicationIDOld = em.merge(applicationIDOld);
            }
            if (applicationIDNew != null && !applicationIDNew.equals(applicationIDOld)) {
                applicationIDNew.getRefereeReportList().add(refereeReport);
                applicationIDNew = em.merge(applicationIDNew);
            }
            if (refereeIDOld != null && !refereeIDOld.equals(refereeIDNew)) {
                refereeIDOld.getRefereeReportList().remove(refereeReport);
                refereeIDOld = em.merge(refereeIDOld);
            }
            if (refereeIDNew != null && !refereeIDNew.equals(refereeIDOld)) {
                refereeIDNew.getRefereeReportList().add(refereeReport);
                refereeIDNew = em.merge(refereeIDNew);
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
                Long id = refereeReport.getReportID();
                if (findRefereeReport(id) == null) {
                    throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.");
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
            RefereeReport refereeReport;
            try {
                refereeReport = em.getReference(RefereeReport.class, id);
                refereeReport.getReportID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.", enfe);
            }
            Application applicationID = refereeReport.getApplicationID();
            if (applicationID != null) {
                applicationID.getRefereeReportList().remove(refereeReport);
                applicationID = em.merge(applicationID);
            }
            Person refereeID = refereeReport.getRefereeID();
            if (refereeID != null) {
                refereeID.getRefereeReportList().remove(refereeReport);
                refereeID = em.merge(refereeID);
            }
            em.remove(refereeReport);
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

    public List<RefereeReport> findRefereeReportEntities() {
        return findRefereeReportEntities(true, -1, -1);
    }

    public List<RefereeReport> findRefereeReportEntities(int maxResults, int firstResult) {
        return findRefereeReportEntities(false, maxResults, firstResult);
    }

    private List<RefereeReport> findRefereeReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RefereeReport.class));
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

    public RefereeReport findRefereeReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RefereeReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getRefereeReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RefereeReport> rt = cq.from(RefereeReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
