/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.DeclineReport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DeclineReportJpaController implements Serializable {

    public DeclineReportJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DeclineReport declineReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = declineReport.getApplication();
        if (applicationOrphanCheck != null) {
            DeclineReport oldDeclineReportOfApplication = applicationOrphanCheck.getDeclineReport();
            if (oldDeclineReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type DeclineReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application application = declineReport.getApplication();
            if (application != null) {
                application = em.getReference(application.getClass(), application.getApplicationID());
                declineReport.setApplication(application);
            }
            em.persist(declineReport);
            if (application != null) {
                application.setDeclineReport(declineReport);
                application = em.merge(application);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDeclineReport(declineReport.getReportID()) != null) {
                throw new PreexistingEntityException("DeclineReport " + declineReport + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DeclineReport declineReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DeclineReport persistentDeclineReport = em.find(DeclineReport.class, declineReport.getReportID());
            Application applicationOld = persistentDeclineReport.getApplication();
            Application applicationNew = declineReport.getApplication();
            List<String> illegalOrphanMessages = null;
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                DeclineReport oldDeclineReportOfApplication = applicationNew.getDeclineReport();
                if (oldDeclineReportOfApplication != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type DeclineReport whose application column cannot be null. Please make another selection for the application field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (applicationNew != null) {
                applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
                declineReport.setApplication(applicationNew);
            }
            declineReport = em.merge(declineReport);
            if (applicationOld != null && !applicationOld.equals(applicationNew)) {
                applicationOld.setDeclineReport(null);
                applicationOld = em.merge(applicationOld);
            }
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                applicationNew.setDeclineReport(declineReport);
                applicationNew = em.merge(applicationNew);
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
                Long id = declineReport.getReportID();
                if (findDeclineReport(id) == null) {
                    throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.");
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
            DeclineReport declineReport;
            try {
                declineReport = em.getReference(DeclineReport.class, id);
                declineReport.getReportID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.", enfe);
            }
            Application application = declineReport.getApplication();
            if (application != null) {
                application.setDeclineReport(null);
                application = em.merge(application);
            }
            em.remove(declineReport);
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

    public List<DeclineReport> findDeclineReportEntities() {
        return findDeclineReportEntities(true, -1, -1);
    }

    public List<DeclineReport> findDeclineReportEntities(int maxResults, int firstResult) {
        return findDeclineReportEntities(false, maxResults, firstResult);
    }

    private List<DeclineReport> findDeclineReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DeclineReport.class));
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

    public DeclineReport findDeclineReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DeclineReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeclineReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DeclineReport> rt = cq.from(DeclineReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
