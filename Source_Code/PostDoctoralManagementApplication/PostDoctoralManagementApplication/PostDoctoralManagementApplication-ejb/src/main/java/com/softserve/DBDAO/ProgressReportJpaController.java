/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
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
import com.softserve.DBEntities.ProgressReport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ProgressReportJpaController implements Serializable {

    public ProgressReportJpaController(EntityManager emm) {

        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(ProgressReport progressReport) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), progressReport);
    }

    public void create(EntityManager em, ProgressReport progressReport) throws RollbackFailureException, Exception 
    {
        em.persist(progressReport);
    }
    
    public ProgressReport edit(ProgressReport progressReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), progressReport);
    }
    
    public ProgressReport edit(EntityManager em, ProgressReport progressReport) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = progressReport.getReportID();
        if (findProgressReport(id) == null) {
            throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.");
        }

        return em.merge(progressReport); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        ProgressReport progressReport;
        try {
            progressReport = em.getReference(ProgressReport.class, id);
            progressReport.getReportID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The progressReport with id " + id + " no longer exists.", enfe);
        }
 
        em.remove(progressReport);
    }

    public List<ProgressReport> findProgressReportEntities() {
        return findProgressReportEntities(true, -1, -1);
    }

    public List<ProgressReport> findProgressReportEntities(int maxResults, int firstResult) {
        return findProgressReportEntities(false, maxResults, firstResult);
    }

    private List<ProgressReport> findProgressReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgressReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public ProgressReport findProgressReport(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgressReport.class, id);
        } finally {
            
        }
    }

    public int getProgressReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgressReport> rt = cq.from(ProgressReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
