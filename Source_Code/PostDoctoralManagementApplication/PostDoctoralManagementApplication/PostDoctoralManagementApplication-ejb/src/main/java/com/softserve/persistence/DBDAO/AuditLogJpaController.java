/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.persistence.DBDAO;

import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.AuditLog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class AuditLogJpaController implements Serializable {

    public AuditLogJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(AuditLog auditLog) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), auditLog);
    }
    
    public void create(EntityManager em,AuditLog auditLog) throws RollbackFailureException, Exception 
    {

        em.persist(auditLog); 

    }
    
    public AuditLog edit(AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        return edit(getEntityManager(), auditLog);
    }

    public AuditLog edit(EntityManager em, AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        Long id = auditLog.getEntryID();
        if (findAuditLog(id) == null) {
            throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.");
        }

        return em.merge(auditLog); 
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager() ,id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        AuditLog auditLog;
        try {
            auditLog = em.getReference(AuditLog.class, id);
            auditLog.getEntryID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.", enfe);
        }

        em.remove(auditLog);

    }

    public List<AuditLog> findAuditLogEntities() {
        return findAuditLogEntities(true, -1, -1);
    }

    public List<AuditLog> findAuditLogEntities(int maxResults, int firstResult) {
        return findAuditLogEntities(false, maxResults, firstResult);
    }

    private List<AuditLog> findAuditLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditLog.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public AuditLog findAuditLog(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditLog.class, id);
        } finally {
            
        }
    }

    public int getAuditLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditLog> rt = cq.from(AuditLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
