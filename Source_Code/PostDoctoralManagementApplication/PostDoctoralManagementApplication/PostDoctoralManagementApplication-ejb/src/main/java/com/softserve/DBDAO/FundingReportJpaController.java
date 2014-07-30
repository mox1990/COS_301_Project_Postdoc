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
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
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
public class FundingReportJpaController implements Serializable {

    public FundingReportJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FundingReport fundingReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Application applicationOrphanCheck = fundingReport.getApplication();
        if (applicationOrphanCheck != null) {
            FundingReport oldFundingReportOfApplication = applicationOrphanCheck.getFundingReport();
            if (oldFundingReportOfApplication != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Application " + applicationOrphanCheck + " already has an item of type FundingReport whose application column cannot be null. Please make another selection for the application field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application application = fundingReport.getApplication();
            if (application != null) {
                application = em.getReference(application.getClass(), application.getApplicationID());
                fundingReport.setApplication(application);
            }
            Person dris = fundingReport.getDris();
            if (dris != null) {
                dris = em.getReference(dris.getClass(), dris.getSystemID());
                fundingReport.setDris(dris);
            }
            em.persist(fundingReport);
            if (application != null) {
                application.setFundingReport(fundingReport);
                application = em.merge(application);
            }
            if (dris != null) {
                dris.getFundingReportList().add(fundingReport);
                dris = em.merge(dris);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFundingReport(fundingReport.getReportID()) != null) {
                throw new PreexistingEntityException("FundingReport " + fundingReport + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FundingReport fundingReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FundingReport persistentFundingReport = em.find(FundingReport.class, fundingReport.getReportID());
            Application applicationOld = persistentFundingReport.getApplication();
            Application applicationNew = fundingReport.getApplication();
            Person drisOld = persistentFundingReport.getDris();
            Person drisNew = fundingReport.getDris();
            List<String> illegalOrphanMessages = null;
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                FundingReport oldFundingReportOfApplication = applicationNew.getFundingReport();
                if (oldFundingReportOfApplication != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Application " + applicationNew + " already has an item of type FundingReport whose application column cannot be null. Please make another selection for the application field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (applicationNew != null) {
                applicationNew = em.getReference(applicationNew.getClass(), applicationNew.getApplicationID());
                fundingReport.setApplication(applicationNew);
            }
            if (drisNew != null) {
                drisNew = em.getReference(drisNew.getClass(), drisNew.getSystemID());
                fundingReport.setDris(drisNew);
            }
            fundingReport = em.merge(fundingReport);
            if (applicationOld != null && !applicationOld.equals(applicationNew)) {
                applicationOld.setFundingReport(null);
                applicationOld = em.merge(applicationOld);
            }
            if (applicationNew != null && !applicationNew.equals(applicationOld)) {
                applicationNew.setFundingReport(fundingReport);
                applicationNew = em.merge(applicationNew);
            }
            if (drisOld != null && !drisOld.equals(drisNew)) {
                drisOld.getFundingReportList().remove(fundingReport);
                drisOld = em.merge(drisOld);
            }
            if (drisNew != null && !drisNew.equals(drisOld)) {
                drisNew.getFundingReportList().add(fundingReport);
                drisNew = em.merge(drisNew);
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
                Long id = fundingReport.getReportID();
                if (findFundingReport(id) == null) {
                    throw new NonexistentEntityException("The fundingReport with id " + id + " no longer exists.");
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
            FundingReport fundingReport;
            try {
                fundingReport = em.getReference(FundingReport.class, id);
                fundingReport.getReportID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fundingReport with id " + id + " no longer exists.", enfe);
            }
            Application application = fundingReport.getApplication();
            if (application != null) {
                application.setFundingReport(null);
                application = em.merge(application);
            }
            Person dris = fundingReport.getDris();
            if (dris != null) {
                dris.getFundingReportList().remove(fundingReport);
                dris = em.merge(dris);
            }
            em.remove(fundingReport);
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

    public List<FundingReport> findFundingReportEntities() {
        return findFundingReportEntities(true, -1, -1);
    }

    public List<FundingReport> findFundingReportEntities(int maxResults, int firstResult) {
        return findFundingReportEntities(false, maxResults, firstResult);
    }

    private List<FundingReport> findFundingReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FundingReport.class));
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

    public FundingReport findFundingReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FundingReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getFundingReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FundingReport> rt = cq.from(FundingReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
