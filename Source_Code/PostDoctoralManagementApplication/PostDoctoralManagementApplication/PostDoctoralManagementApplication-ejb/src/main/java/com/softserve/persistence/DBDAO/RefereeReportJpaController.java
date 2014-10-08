/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RefereeReport;
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

    public RefereeReportJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(RefereeReport refereeReport) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), refereeReport);
    }

    public void create(EntityManager em, RefereeReport refereeReport) throws RollbackFailureException, Exception 
    {
        em.persist(refereeReport);
    }
    
    public RefereeReport edit(RefereeReport refereeReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), refereeReport);
    }

    public RefereeReport edit(EntityManager em, RefereeReport refereeReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        Long id = refereeReport.getReportID();
        if (findRefereeReport(id) == null) {
            throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.");
        }
        
        return em.merge(refereeReport);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        
        RefereeReport refereeReport;
        try {
            refereeReport = em.getReference(RefereeReport.class, id);
            refereeReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The refereeReport with id " + id + " no longer exists.", enfe);
        }
        
        em.remove(refereeReport);            
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
            
        }
    }

    public RefereeReport findRefereeReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RefereeReport.class, id);
        } finally {
            
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
            
        }
    }
    
}
