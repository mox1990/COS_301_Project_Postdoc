/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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

    public MinuteCommentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MinuteComment minuteComment) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CommitteeMeeting meetingID = minuteComment.getMeetingID();
            if (meetingID != null) {
                meetingID = em.getReference(meetingID.getClass(), meetingID.getMeetingID());
                minuteComment.setMeetingID(meetingID);
            }
            Person attendeeID = minuteComment.getAttendeeID();
            if (attendeeID != null) {
                attendeeID = em.getReference(attendeeID.getClass(), attendeeID.getSystemID());
                minuteComment.setAttendeeID(attendeeID);
            }
            em.persist(minuteComment);
            if (meetingID != null) {
                meetingID.getMinuteCommentCollection().add(minuteComment);
                meetingID = em.merge(meetingID);
            }
            if (attendeeID != null) {
                attendeeID.getMinuteCommentCollection().add(minuteComment);
                attendeeID = em.merge(attendeeID);
            }
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

    public void edit(MinuteComment minuteComment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MinuteComment persistentMinuteComment = em.find(MinuteComment.class, minuteComment.getCommentID());
            CommitteeMeeting meetingIDOld = persistentMinuteComment.getMeetingID();
            CommitteeMeeting meetingIDNew = minuteComment.getMeetingID();
            Person attendeeIDOld = persistentMinuteComment.getAttendeeID();
            Person attendeeIDNew = minuteComment.getAttendeeID();
            if (meetingIDNew != null) {
                meetingIDNew = em.getReference(meetingIDNew.getClass(), meetingIDNew.getMeetingID());
                minuteComment.setMeetingID(meetingIDNew);
            }
            if (attendeeIDNew != null) {
                attendeeIDNew = em.getReference(attendeeIDNew.getClass(), attendeeIDNew.getSystemID());
                minuteComment.setAttendeeID(attendeeIDNew);
            }
            minuteComment = em.merge(minuteComment);
            if (meetingIDOld != null && !meetingIDOld.equals(meetingIDNew)) {
                meetingIDOld.getMinuteCommentCollection().remove(minuteComment);
                meetingIDOld = em.merge(meetingIDOld);
            }
            if (meetingIDNew != null && !meetingIDNew.equals(meetingIDOld)) {
                meetingIDNew.getMinuteCommentCollection().add(minuteComment);
                meetingIDNew = em.merge(meetingIDNew);
            }
            if (attendeeIDOld != null && !attendeeIDOld.equals(attendeeIDNew)) {
                attendeeIDOld.getMinuteCommentCollection().remove(minuteComment);
                attendeeIDOld = em.merge(attendeeIDOld);
            }
            if (attendeeIDNew != null && !attendeeIDNew.equals(attendeeIDOld)) {
                attendeeIDNew.getMinuteCommentCollection().add(minuteComment);
                attendeeIDNew = em.merge(attendeeIDNew);
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
                Long id = minuteComment.getCommentID();
                if (findMinuteComment(id) == null) {
                    throw new NonexistentEntityException("The minuteComment with id " + id + " no longer exists.");
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
            MinuteComment minuteComment;
            try {
                minuteComment = em.getReference(MinuteComment.class, id);
                minuteComment.getCommentID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The minuteComment with id " + id + " no longer exists.", enfe);
            }
            CommitteeMeeting meetingID = minuteComment.getMeetingID();
            if (meetingID != null) {
                meetingID.getMinuteCommentCollection().remove(minuteComment);
                meetingID = em.merge(meetingID);
            }
            Person attendeeID = minuteComment.getAttendeeID();
            if (attendeeID != null) {
                attendeeID.getMinuteCommentCollection().remove(minuteComment);
                attendeeID = em.merge(attendeeID);
            }
            em.remove(minuteComment);
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
            em.close();
        }
    }

    public MinuteComment findMinuteComment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MinuteComment.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
}
