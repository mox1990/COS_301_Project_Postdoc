/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ForwardAndRewindReport;
import com.softserve.persistence.DBEntities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ForwardAndRewindReportJpaController implements Serializable {

    public ForwardAndRewindReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(ForwardAndRewindReport forwardAndRewindReport) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), forwardAndRewindReport);
    }

    public void create(EntityManager em, ForwardAndRewindReport forwardAndRewindReport) throws RollbackFailureException, Exception 
    {
        em.persist(forwardAndRewindReport);
    }
    
    public ForwardAndRewindReport edit(ForwardAndRewindReport forwardAndRewindReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), forwardAndRewindReport);
    }
    
    public ForwardAndRewindReport edit(EntityManager em, ForwardAndRewindReport forwardAndRewindReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        Long id = forwardAndRewindReport.getReportID();
        if (findForwardAndRewindReport(id) == null) {
            throw new NonexistentEntityException("The forwardAndRewindReport with id " + id + " no longer exists.");
        }

        return em.merge(forwardAndRewindReport);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }
    
    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        ForwardAndRewindReport forwardAndRewindReport;
        try {
            forwardAndRewindReport = em.getReference(ForwardAndRewindReport.class, id);
            forwardAndRewindReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The forwardAndRewindReport with id " + id + " no longer exists.", enfe);
        }

        em.remove(forwardAndRewindReport);
           
    }

    public List<ForwardAndRewindReport> findForwardAndRewindReportEntities() {
        return findForwardAndRewindReportEntities(true, -1, -1);
    }

    public List<ForwardAndRewindReport> findForwardAndRewindReportEntities(int maxResults, int firstResult) {
        return findForwardAndRewindReportEntities(false, maxResults, firstResult);
    }

    private List<ForwardAndRewindReport> findForwardAndRewindReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ForwardAndRewindReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public ForwardAndRewindReport findForwardAndRewindReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ForwardAndRewindReport.class, id);
        } finally {
            
        }
    }

    public int getForwardAndRewindReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ForwardAndRewindReport> rt = cq.from(ForwardAndRewindReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
