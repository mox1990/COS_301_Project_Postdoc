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
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.FundingReport;
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

    public void create(FundingReport fundingReport) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (fundingReport.getApplicationList() == null) {
            fundingReport.setApplicationList(new ArrayList<Application>());
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
            List<Application> attachedApplicationList = new ArrayList<Application>();
            for (Application applicationListApplicationToAttach : fundingReport.getApplicationList()) {
                applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
                attachedApplicationList.add(applicationListApplicationToAttach);
            }
            fundingReport.setApplicationList(attachedApplicationList);
            em.persist(fundingReport);
            if (drisID != null) {
                drisID.getFundingReportList().add(fundingReport);
                drisID = em.merge(drisID);
            }
            for (Application applicationListApplication : fundingReport.getApplicationList()) {
                FundingReport oldFundingReportIDOfApplicationListApplication = applicationListApplication.getFundingReportID();
                applicationListApplication.setFundingReportID(fundingReport);
                applicationListApplication = em.merge(applicationListApplication);
                if (oldFundingReportIDOfApplicationListApplication != null) {
                    oldFundingReportIDOfApplicationListApplication.getApplicationList().remove(applicationListApplication);
                    oldFundingReportIDOfApplicationListApplication = em.merge(oldFundingReportIDOfApplicationListApplication);
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
            List<Application> applicationListOld = persistentFundingReport.getApplicationList();
            List<Application> applicationListNew = fundingReport.getApplicationList();
            if (drisIDNew != null) {
                drisIDNew = em.getReference(drisIDNew.getClass(), drisIDNew.getSystemID());
                fundingReport.setDrisID(drisIDNew);
            }
            List<Application> attachedApplicationListNew = new ArrayList<Application>();
            for (Application applicationListNewApplicationToAttach : applicationListNew) {
                applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
                attachedApplicationListNew.add(applicationListNewApplicationToAttach);
            }
            applicationListNew = attachedApplicationListNew;
            fundingReport.setApplicationList(applicationListNew);
            fundingReport = em.merge(fundingReport);
            if (drisIDOld != null && !drisIDOld.equals(drisIDNew)) {
                drisIDOld.getFundingReportList().remove(fundingReport);
                drisIDOld = em.merge(drisIDOld);
            }
            if (drisIDNew != null && !drisIDNew.equals(drisIDOld)) {
                drisIDNew.getFundingReportList().add(fundingReport);
                drisIDNew = em.merge(drisIDNew);
            }
            for (Application applicationListOldApplication : applicationListOld) {
                if (!applicationListNew.contains(applicationListOldApplication)) {
                    applicationListOldApplication.setFundingReportID(null);
                    applicationListOldApplication = em.merge(applicationListOldApplication);
                }
            }
            for (Application applicationListNewApplication : applicationListNew) {
                if (!applicationListOld.contains(applicationListNewApplication)) {
                    FundingReport oldFundingReportIDOfApplicationListNewApplication = applicationListNewApplication.getFundingReportID();
                    applicationListNewApplication.setFundingReportID(fundingReport);
                    applicationListNewApplication = em.merge(applicationListNewApplication);
                    if (oldFundingReportIDOfApplicationListNewApplication != null && !oldFundingReportIDOfApplicationListNewApplication.equals(fundingReport)) {
                        oldFundingReportIDOfApplicationListNewApplication.getApplicationList().remove(applicationListNewApplication);
                        oldFundingReportIDOfApplicationListNewApplication = em.merge(oldFundingReportIDOfApplicationListNewApplication);
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
                drisID.getFundingReportList().remove(fundingReport);
                drisID = em.merge(drisID);
            }
            List<Application> applicationList = fundingReport.getApplicationList();
            for (Application applicationListApplication : applicationList) {
                applicationListApplication.setFundingReportID(null);
                applicationListApplication = em.merge(applicationListApplication);
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
