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
import com.softserve.DBEnties.Person;
import com.softserve.DBEnties.Application;
import com.softserve.DBEnties.FundingReport;
import java.util.ArrayList;
import java.util.Collection;
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

    public void create(FundingReport fundingReport) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (fundingReport.getApplicationCollection() == null) {
            fundingReport.setApplicationCollection(new ArrayList<Application>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person drisID = fundingReport.getDrisID();
            if (drisID != null) {
                drisID = em.getReference(drisID.getClass(), drisID.getSystemID());
                fundingReport.setDrisID(drisID);
            }
            Collection<Application> attachedApplicationCollection = new ArrayList<Application>();
            for (Application applicationCollectionApplicationToAttach : fundingReport.getApplicationCollection()) {
                applicationCollectionApplicationToAttach = em.getReference(applicationCollectionApplicationToAttach.getClass(), applicationCollectionApplicationToAttach.getApplicationID());
                attachedApplicationCollection.add(applicationCollectionApplicationToAttach);
            }
            fundingReport.setApplicationCollection(attachedApplicationCollection);
            em.persist(fundingReport);
            if (drisID != null) {
                drisID.getFundingReportCollection().add(fundingReport);
                drisID = em.merge(drisID);
            }
            for (Application applicationCollectionApplication : fundingReport.getApplicationCollection()) {
                FundingReport oldFundingReportIDOfApplicationCollectionApplication = applicationCollectionApplication.getFundingReportID();
                applicationCollectionApplication.setFundingReportID(fundingReport);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
                if (oldFundingReportIDOfApplicationCollectionApplication != null) {
                    oldFundingReportIDOfApplicationCollectionApplication.getApplicationCollection().remove(applicationCollectionApplication);
                    oldFundingReportIDOfApplicationCollectionApplication = em.merge(oldFundingReportIDOfApplicationCollectionApplication);
                }
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

    public void edit(FundingReport fundingReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FundingReport persistentFundingReport = em.find(FundingReport.class, fundingReport.getReportID());
            Person drisIDOld = persistentFundingReport.getDrisID();
            Person drisIDNew = fundingReport.getDrisID();
            Collection<Application> applicationCollectionOld = persistentFundingReport.getApplicationCollection();
            Collection<Application> applicationCollectionNew = fundingReport.getApplicationCollection();
            if (drisIDNew != null) {
                drisIDNew = em.getReference(drisIDNew.getClass(), drisIDNew.getSystemID());
                fundingReport.setDrisID(drisIDNew);
            }
            Collection<Application> attachedApplicationCollectionNew = new ArrayList<Application>();
            for (Application applicationCollectionNewApplicationToAttach : applicationCollectionNew) {
                applicationCollectionNewApplicationToAttach = em.getReference(applicationCollectionNewApplicationToAttach.getClass(), applicationCollectionNewApplicationToAttach.getApplicationID());
                attachedApplicationCollectionNew.add(applicationCollectionNewApplicationToAttach);
            }
            applicationCollectionNew = attachedApplicationCollectionNew;
            fundingReport.setApplicationCollection(applicationCollectionNew);
            fundingReport = em.merge(fundingReport);
            if (drisIDOld != null && !drisIDOld.equals(drisIDNew)) {
                drisIDOld.getFundingReportCollection().remove(fundingReport);
                drisIDOld = em.merge(drisIDOld);
            }
            if (drisIDNew != null && !drisIDNew.equals(drisIDOld)) {
                drisIDNew.getFundingReportCollection().add(fundingReport);
                drisIDNew = em.merge(drisIDNew);
            }
            for (Application applicationCollectionOldApplication : applicationCollectionOld) {
                if (!applicationCollectionNew.contains(applicationCollectionOldApplication)) {
                    applicationCollectionOldApplication.setFundingReportID(null);
                    applicationCollectionOldApplication = em.merge(applicationCollectionOldApplication);
                }
            }
            for (Application applicationCollectionNewApplication : applicationCollectionNew) {
                if (!applicationCollectionOld.contains(applicationCollectionNewApplication)) {
                    FundingReport oldFundingReportIDOfApplicationCollectionNewApplication = applicationCollectionNewApplication.getFundingReportID();
                    applicationCollectionNewApplication.setFundingReportID(fundingReport);
                    applicationCollectionNewApplication = em.merge(applicationCollectionNewApplication);
                    if (oldFundingReportIDOfApplicationCollectionNewApplication != null && !oldFundingReportIDOfApplicationCollectionNewApplication.equals(fundingReport)) {
                        oldFundingReportIDOfApplicationCollectionNewApplication.getApplicationCollection().remove(applicationCollectionNewApplication);
                        oldFundingReportIDOfApplicationCollectionNewApplication = em.merge(oldFundingReportIDOfApplicationCollectionNewApplication);
                    }
                }
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
            Person drisID = fundingReport.getDrisID();
            if (drisID != null) {
                drisID.getFundingReportCollection().remove(fundingReport);
                drisID = em.merge(drisID);
            }
            Collection<Application> applicationCollection = fundingReport.getApplicationCollection();
            for (Application applicationCollectionApplication : applicationCollection) {
                applicationCollectionApplication.setFundingReportID(null);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
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
