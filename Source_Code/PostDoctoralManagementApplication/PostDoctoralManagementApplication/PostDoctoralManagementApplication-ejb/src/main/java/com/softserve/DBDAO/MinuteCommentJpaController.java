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
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class MinuteCommentJpaController implements Serializable {

    public MinuteCommentJpaController(EntityManager emm) {
        this.emm = emm;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(MinuteComment minuteComment) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), minuteComment);
    }
    
    public void create(EntityManager em, MinuteComment minuteComment) throws RollbackFailureException, Exception 
    {
        em.persist(minuteComment);
    }
    
    public MinuteComment edit(MinuteComment minuteComment) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), minuteComment);
    }

    public MinuteComment edit(EntityManager em, MinuteComment minuteComment) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        Long id = minuteComment.getCommentID();
        if (findMinuteComment(id) == null) {
            throw new NonexistentEntityException("The minuteComment with id " + id + " no longer exists.");
        }
                
        return em.merge(minuteComment);
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws NonexistentEntityException, RollbackFailureException, Exception 
    {

        MinuteComment minuteComment;
        try {
            minuteComment = em.getReference(MinuteComment.class, id);
            minuteComment.getCommentID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The minuteComment with id " + id + " no longer exists.", enfe);
        }

        em.remove(minuteComment);            
    }

    public List<MinuteComment> findMinuteCommentEntities() {
        return findMinuteCommentEntities(true, -1, -1);
    }

    public List<MinuteComment> findMinuteCommentEntities(int maxResults, int firstResult) {
        return findMinuteCommentEntities(false, maxResults, firstResult);
    }

    private List<MinuteComment> findMinuteCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MinuteComment.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public MinuteComment findMinuteComment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MinuteComment.class, id);
        } finally {
            
        }
    }

    public int getMinuteCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MinuteComment> rt = cq.from(MinuteComment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
}
