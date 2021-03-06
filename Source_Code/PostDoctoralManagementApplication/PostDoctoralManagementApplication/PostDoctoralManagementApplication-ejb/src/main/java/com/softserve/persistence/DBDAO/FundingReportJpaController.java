/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.FundingReport;
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

    public FundingReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(FundingReport fundingReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), fundingReport);
    }
    
    public void create(EntityManager em, FundingReport fundingReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
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

        em.persist(fundingReport);

    }
    
    public FundingReport edit(FundingReport fundingReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), fundingReport);
    }
    
    public FundingReport edit(EntityManager em, FundingReport fundingReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

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

        return em.merge(fundingReport);
 
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }
    
    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {

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
            
        }
    }

    public FundingReport findFundingReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FundingReport.class, id);
        } finally {
            
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
           
        }
    }
    
}
