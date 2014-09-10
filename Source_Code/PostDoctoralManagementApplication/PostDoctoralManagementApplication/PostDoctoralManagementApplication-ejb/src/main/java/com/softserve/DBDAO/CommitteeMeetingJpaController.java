/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
import java.util.List;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CommitteeMeetingJpaController implements Serializable {

    public CommitteeMeetingJpaController(EntityManager em) {
        this.emm = em;
    }

    private EntityManager emm = null;

    public EntityManager getEntityManager() {
        return emm;
    }
    
    public void create(CommitteeMeeting committeeMeeting) throws RollbackFailureException, Exception 
    {
        create(getEntityManager(), committeeMeeting);
    }
        
    public void create(EntityManager em, CommitteeMeeting committeeMeeting) throws RollbackFailureException, Exception {
        if (committeeMeeting.getPersonList() == null) {
            committeeMeeting.setPersonList(new ArrayList<Person>());
        }
        if (committeeMeeting.getApplicationList() == null) {
            committeeMeeting.setApplicationList(new ArrayList<Application>());
        }
        if (committeeMeeting.getMinuteCommentList() == null) {
            committeeMeeting.setMinuteCommentList(new ArrayList<MinuteComment>());
        }

        em.persist(committeeMeeting);
       
    }
    
    public CommitteeMeeting edit(CommitteeMeeting committeeMeeting) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        return edit(getEntityManager(), committeeMeeting);
    }

    public CommitteeMeeting edit(EntityManager em, CommitteeMeeting committeeMeeting) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        Long id = committeeMeeting.getMeetingID();
        if (findCommitteeMeeting(id) == null) {
            throw new NonexistentEntityException("The committeeMeeting with id " + id + " no longer exists.");
        }
                
        CommitteeMeeting persistentCommitteeMeeting = em.find(CommitteeMeeting.class, committeeMeeting.getMeetingID());

        List<MinuteComment> minuteCommentListOld = persistentCommitteeMeeting.getMinuteCommentList();
        List<MinuteComment> minuteCommentListNew = committeeMeeting.getMinuteCommentList();
        List<String> illegalOrphanMessages = null;
        for (MinuteComment minuteCommentListOldMinuteComment : minuteCommentListOld) {
            if (!minuteCommentListNew.contains(minuteCommentListOldMinuteComment)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentListOldMinuteComment + " since its meeting field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        return em.merge(committeeMeeting);
       

    }
    
    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        destroy(getEntityManager(), id);
    }

    public void destroy(EntityManager em, Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        CommitteeMeeting committeeMeeting;
        try {
            committeeMeeting = em.getReference(CommitteeMeeting.class, id);
            committeeMeeting.getMeetingID();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The committeeMeeting with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<MinuteComment> minuteCommentListOrphanCheck = committeeMeeting.getMinuteCommentList();
        for (MinuteComment minuteCommentListOrphanCheckMinuteComment : minuteCommentListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This CommitteeMeeting (" + committeeMeeting + ") cannot be destroyed since the MinuteComment " + minuteCommentListOrphanCheckMinuteComment + " in its minuteCommentList field has a non-nullable meeting field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        
        em.remove(committeeMeeting);

    }

    public List<CommitteeMeeting> findCommitteeMeetingEntities() {
        return findCommitteeMeetingEntities(true, -1, -1);
    }

    public List<CommitteeMeeting> findCommitteeMeetingEntities(int maxResults, int firstResult) {
        return findCommitteeMeetingEntities(false, maxResults, firstResult);
    }

    private List<CommitteeMeeting> findCommitteeMeetingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CommitteeMeeting.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }

    public CommitteeMeeting findCommitteeMeeting(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CommitteeMeeting.class, id);
        } finally {
            
        }
    }

    public int getCommitteeMeetingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CommitteeMeeting> rt = cq.from(CommitteeMeeting.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            
        }
    }
    
    public List<CommitteeMeeting> findAllStillToBeHeldCommitteeMeetings()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<CommitteeMeeting> q = em.createQuery("SELECT c FROM CommitteeMeeting c WHERE CURRENT_TIMESTAMP < c.startDate", CommitteeMeeting.class);
        return q.getResultList();
    }
    
    public List<CommitteeMeeting> findAllActiveCommitteeMeetings()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<CommitteeMeeting> q = em.createQuery("SELECT c FROM CommitteeMeeting c WHERE (CURRENT_TIMESTAMP >= c.startDate AND c.endDate IS NULL) OR (CURRENT_TIMESTAMP BETWEEN c.startDate AND c.endDate)", CommitteeMeeting.class);
        return q.getResultList();
    }
    
    public List<CommitteeMeeting> findAllConcludedCommitteeMeetings()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<CommitteeMeeting> q = em.createQuery("SELECT c FROM CommitteeMeeting c WHERE c.endDate IS NOT NULL", CommitteeMeeting.class);
        return q.getResultList();
    }
}
