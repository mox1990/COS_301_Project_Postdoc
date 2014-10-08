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
import com.softserve.persistence.DBEntities.DeclineReport;
import com.softserve.persistence.DBEntities.Person;
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

    public DeclineReportJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(DeclineReport declineReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception 
    {
        create(getEntityManager(), declineReport);
    }
    
    public void create(EntityManager em, DeclineReport declineReport) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
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
        
        em.persist(declineReport);            
    }
    
    public DeclineReport edit(DeclineReport declineReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), declineReport);
    }

    public DeclineReport edit(EntityManager em, DeclineReport declineReport) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Long id = declineReport.getReportID();
        if (findDeclineReport(id) == null) {
            throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.");
        }
        
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

        return em.merge(declineReport);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        DeclineReport declineReport;
        try {
            declineReport = em.getReference(DeclineReport.class, id);
            declineReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The declineReport with id " + id + " no longer exists.", enfe);
        }

        em.remove(declineReport);
           
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
            
        }
    }

    public DeclineReport findDeclineReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DeclineReport.class, id);
        } finally {
            
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
            
        }
    }
    
}
