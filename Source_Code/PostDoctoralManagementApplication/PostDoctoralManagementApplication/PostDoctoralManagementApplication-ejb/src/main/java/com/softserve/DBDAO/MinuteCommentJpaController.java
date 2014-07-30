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
            CommitteeMeeting meeting = minuteComment.getMeeting();
            if (meeting != null) {
                meeting = em.getReference(meeting.getClass(), meeting.getMeetingID());
                minuteComment.setMeeting(meeting);
            }
            Person attendee = minuteComment.getAttendee();
            if (attendee != null) {
                attendee = em.getReference(attendee.getClass(), attendee.getSystemID());
                minuteComment.setAttendee(attendee);
            }
            em.persist(minuteComment);
            if (meeting != null) {
                meeting.getMinuteCommentList().add(minuteComment);
                meeting = em.merge(meeting);
            }
            if (attendee != null) {
                attendee.getMinuteCommentList().add(minuteComment);
                attendee = em.merge(attendee);
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
            CommitteeMeeting meetingOld = persistentMinuteComment.getMeeting();
            CommitteeMeeting meetingNew = minuteComment.getMeeting();
            Person attendeeOld = persistentMinuteComment.getAttendee();
            Person attendeeNew = minuteComment.getAttendee();
            if (meetingNew != null) {
                meetingNew = em.getReference(meetingNew.getClass(), meetingNew.getMeetingID());
                minuteComment.setMeeting(meetingNew);
            }
            if (attendeeNew != null) {
                attendeeNew = em.getReference(attendeeNew.getClass(), attendeeNew.getSystemID());
                minuteComment.setAttendee(attendeeNew);
            }
            minuteComment = em.merge(minuteComment);
            if (meetingOld != null && !meetingOld.equals(meetingNew)) {
                meetingOld.getMinuteCommentList().remove(minuteComment);
                meetingOld = em.merge(meetingOld);
            }
            if (meetingNew != null && !meetingNew.equals(meetingOld)) {
                meetingNew.getMinuteCommentList().add(minuteComment);
                meetingNew = em.merge(meetingNew);
            }
            if (attendeeOld != null && !attendeeOld.equals(attendeeNew)) {
                attendeeOld.getMinuteCommentList().remove(minuteComment);
                attendeeOld = em.merge(attendeeOld);
            }
            if (attendeeNew != null && !attendeeNew.equals(attendeeOld)) {
                attendeeNew.getMinuteCommentList().add(minuteComment);
                attendeeNew = em.merge(attendeeNew);
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
            CommitteeMeeting meeting = minuteComment.getMeeting();
            if (meeting != null) {
                meeting.getMinuteCommentList().remove(minuteComment);
                meeting = em.merge(meeting);
            }
            Person attendee = minuteComment.getAttendee();
            if (attendee != null) {
                attendee.getMinuteCommentList().remove(minuteComment);
                attendee = em.merge(attendee);
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
