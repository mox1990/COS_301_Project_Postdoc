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
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.FundingCost;
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

    public FundingReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntityManager em, FundingReport fundingReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (fundingReport.getFundingCostList() == null) {
            fundingReport.setFundingCostList(new ArrayList<FundingCost>());
        }
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
        List<FundingCost> attachedFundingCostList = new ArrayList<FundingCost>();
        for (FundingCost fundingCostListFundingCostToAttach : fundingReport.getFundingCostList()) {
            fundingCostListFundingCostToAttach = em.getReference(fundingCostListFundingCostToAttach.getClass(), fundingCostListFundingCostToAttach.getCostID());
            attachedFundingCostList.add(fundingCostListFundingCostToAttach);
        }
        fundingReport.setFundingCostList(attachedFundingCostList);
        em.persist(fundingReport);
        if (application != null) {
            application.setFundingReport(fundingReport);
            application = em.merge(application);
        }
        if (dris != null) {
            dris.getFundingReportList().add(fundingReport);
            dris = em.merge(dris);
        }
        for (FundingCost fundingCostListFundingCost : fundingReport.getFundingCostList()) {
            FundingReport oldFundingReportOfFundingCostListFundingCost = fundingCostListFundingCost.getFundingReport();
            fundingCostListFundingCost.setFundingReport(fundingReport);
            fundingCostListFundingCost = em.merge(fundingCostListFundingCost);
            if (oldFundingReportOfFundingCostListFundingCost != null) {
                oldFundingReportOfFundingCostListFundingCost.getFundingCostList().remove(fundingCostListFundingCost);
                oldFundingReportOfFundingCostListFundingCost = em.merge(oldFundingReportOfFundingCostListFundingCost);
            }
        }
    }

    public void edit(EntityManager em, FundingReport fundingReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        Long id = fundingReport.getReportID();
        if (findFundingReport(id) == null) {
            throw new NonexistentEntityException("The fundingReport with id " + id + " no longer exists.");
        }
        
        FundingReport persistentFundingReport = em.find(FundingReport.class, fundingReport.getReportID());
        Application applicationOld = persistentFundingReport.getApplication();
        Application applicationNew = fundingReport.getApplication();
        Person drisOld = persistentFundingReport.getDris();
        Person drisNew = fundingReport.getDris();
        List<FundingCost> fundingCostListOld = persistentFundingReport.getFundingCostList();
        List<FundingCost> fundingCostListNew = fundingReport.getFundingCostList();
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
        for (FundingCost fundingCostListOldFundingCost : fundingCostListOld) {
            if (!fundingCostListNew.contains(fundingCostListOldFundingCost)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FundingCost " + fundingCostListOldFundingCost + " since its fundingReport field is not nullable.");
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
        List<FundingCost> attachedFundingCostListNew = new ArrayList<FundingCost>();
        for (FundingCost fundingCostListNewFundingCostToAttach : fundingCostListNew) {
            fundingCostListNewFundingCostToAttach = em.getReference(fundingCostListNewFundingCostToAttach.getClass(), fundingCostListNewFundingCostToAttach.getCostID());
            attachedFundingCostListNew.add(fundingCostListNewFundingCostToAttach);
        }
        fundingCostListNew = attachedFundingCostListNew;
        fundingReport.setFundingCostList(fundingCostListNew);
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
        for (FundingCost fundingCostListNewFundingCost : fundingCostListNew) {
            if (!fundingCostListOld.contains(fundingCostListNewFundingCost)) {
                FundingReport oldFundingReportOfFundingCostListNewFundingCost = fundingCostListNewFundingCost.getFundingReport();
                fundingCostListNewFundingCost.setFundingReport(fundingReport);
                fundingCostListNewFundingCost = em.merge(fundingCostListNewFundingCost);
                if (oldFundingReportOfFundingCostListNewFundingCost != null && !oldFundingReportOfFundingCostListNewFundingCost.equals(fundingReport)) {
                    oldFundingReportOfFundingCostListNewFundingCost.getFundingCostList().remove(fundingCostListNewFundingCost);
                    oldFundingReportOfFundingCostListNewFundingCost = em.merge(oldFundingReportOfFundingCostListNewFundingCost);
                }
            }
        }
            
                
           
    }

    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        FundingReport fundingReport;
        try {
            fundingReport = em.getReference(FundingReport.class, id);
            fundingReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The fundingReport with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<FundingCost> fundingCostListOrphanCheck = fundingReport.getFundingCostList();
        for (FundingCost fundingCostListOrphanCheckFundingCost : fundingCostListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This FundingReport (" + fundingReport + ") cannot be destroyed since the FundingCost " + fundingCostListOrphanCheckFundingCost + " in its fundingCostList field has a non-nullable fundingReport field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
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
