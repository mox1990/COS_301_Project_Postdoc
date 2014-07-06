/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.DBDAO;

import com.softserve.DBDAO.exceptions.IllegalOrphanException;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CommitteeMeetingJpaController implements Serializable {

    public CommitteeMeetingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CommitteeMeeting committeeMeeting) throws RollbackFailureException, Exception {
        if (committeeMeeting.getPersonList() == null) {
            committeeMeeting.setPersonList(new ArrayList<Person>());
        }
        if (committeeMeeting.getApplicationList() == null) {
            committeeMeeting.setApplicationList(new ArrayList<Application>());
        }
        if (committeeMeeting.getMinuteCommentList() == null) {
            committeeMeeting.setMinuteCommentList(new ArrayList<MinuteComment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Person> attachedPersonList = new ArrayList<Person>();
            for (Person personListPersonToAttach : committeeMeeting.getPersonList()) {
                personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getSystemID());
                attachedPersonList.add(personListPersonToAttach);
            }
            committeeMeeting.setPersonList(attachedPersonList);
            List<Application> attachedApplicationList = new ArrayList<Application>();
            for (Application applicationListApplicationToAttach : committeeMeeting.getApplicationList()) {
                applicationListApplicationToAttach = em.getReference(applicationListApplicationToAttach.getClass(), applicationListApplicationToAttach.getApplicationID());
                attachedApplicationList.add(applicationListApplicationToAttach);
            }
            committeeMeeting.setApplicationList(attachedApplicationList);
            List<MinuteComment> attachedMinuteCommentList = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentListMinuteCommentToAttach : committeeMeeting.getMinuteCommentList()) {
                minuteCommentListMinuteCommentToAttach = em.getReference(minuteCommentListMinuteCommentToAttach.getClass(), minuteCommentListMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentList.add(minuteCommentListMinuteCommentToAttach);
            }
            committeeMeeting.setMinuteCommentList(attachedMinuteCommentList);
            em.persist(committeeMeeting);
            for (Person personListPerson : committeeMeeting.getPersonList()) {
                personListPerson.getCommitteeMeetingList().add(committeeMeeting);
                personListPerson = em.merge(personListPerson);
            }
            for (Application applicationListApplication : committeeMeeting.getApplicationList()) {
                applicationListApplication.getCommitteeMeetingList().add(committeeMeeting);
                applicationListApplication = em.merge(applicationListApplication);
            }
            for (MinuteComment minuteCommentListMinuteComment : committeeMeeting.getMinuteCommentList()) {
                CommitteeMeeting oldMeetingIDOfMinuteCommentListMinuteComment = minuteCommentListMinuteComment.getMeetingID();
                minuteCommentListMinuteComment.setMeetingID(committeeMeeting);
                minuteCommentListMinuteComment = em.merge(minuteCommentListMinuteComment);
                if (oldMeetingIDOfMinuteCommentListMinuteComment != null) {
                    oldMeetingIDOfMinuteCommentListMinuteComment.getMinuteCommentList().remove(minuteCommentListMinuteComment);
                    oldMeetingIDOfMinuteCommentListMinuteComment = em.merge(oldMeetingIDOfMinuteCommentListMinuteComment);
                }
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

    public void edit(CommitteeMeeting committeeMeeting) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CommitteeMeeting persistentCommitteeMeeting = em.find(CommitteeMeeting.class, committeeMeeting.getMeetingID());
            List<Person> personListOld = persistentCommitteeMeeting.getPersonList();
            List<Person> personListNew = committeeMeeting.getPersonList();
            List<Application> applicationListOld = persistentCommitteeMeeting.getApplicationList();
            List<Application> applicationListNew = committeeMeeting.getApplicationList();
            List<MinuteComment> minuteCommentListOld = persistentCommitteeMeeting.getMinuteCommentList();
            List<MinuteComment> minuteCommentListNew = committeeMeeting.getMinuteCommentList();
            List<String> illegalOrphanMessages = null;
            for (MinuteComment minuteCommentListOldMinuteComment : minuteCommentListOld) {
                if (!minuteCommentListNew.contains(minuteCommentListOldMinuteComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentListOldMinuteComment + " since its meetingID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Person> attachedPersonListNew = new ArrayList<Person>();
            for (Person personListNewPersonToAttach : personListNew) {
                personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getSystemID());
                attachedPersonListNew.add(personListNewPersonToAttach);
            }
            personListNew = attachedPersonListNew;
            committeeMeeting.setPersonList(personListNew);
            List<Application> attachedApplicationListNew = new ArrayList<Application>();
            for (Application applicationListNewApplicationToAttach : applicationListNew) {
                applicationListNewApplicationToAttach = em.getReference(applicationListNewApplicationToAttach.getClass(), applicationListNewApplicationToAttach.getApplicationID());
                attachedApplicationListNew.add(applicationListNewApplicationToAttach);
            }
            applicationListNew = attachedApplicationListNew;
            committeeMeeting.setApplicationList(applicationListNew);
            List<MinuteComment> attachedMinuteCommentListNew = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentListNewMinuteCommentToAttach : minuteCommentListNew) {
                minuteCommentListNewMinuteCommentToAttach = em.getReference(minuteCommentListNewMinuteCommentToAttach.getClass(), minuteCommentListNewMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentListNew.add(minuteCommentListNewMinuteCommentToAttach);
            }
            minuteCommentListNew = attachedMinuteCommentListNew;
            committeeMeeting.setMinuteCommentList(minuteCommentListNew);
            committeeMeeting = em.merge(committeeMeeting);
            for (Person personListOldPerson : personListOld) {
                if (!personListNew.contains(personListOldPerson)) {
                    personListOldPerson.getCommitteeMeetingList().remove(committeeMeeting);
                    personListOldPerson = em.merge(personListOldPerson);
                }
            }
            for (Person personListNewPerson : personListNew) {
                if (!personListOld.contains(personListNewPerson)) {
                    personListNewPerson.getCommitteeMeetingList().add(committeeMeeting);
                    personListNewPerson = em.merge(personListNewPerson);
                }
            }
            for (Application applicationListOldApplication : applicationListOld) {
                if (!applicationListNew.contains(applicationListOldApplication)) {
                    applicationListOldApplication.getCommitteeMeetingList().remove(committeeMeeting);
                    applicationListOldApplication = em.merge(applicationListOldApplication);
                }
            }
            for (Application applicationListNewApplication : applicationListNew) {
                if (!applicationListOld.contains(applicationListNewApplication)) {
                    applicationListNewApplication.getCommitteeMeetingList().add(committeeMeeting);
                    applicationListNewApplication = em.merge(applicationListNewApplication);
                }
            }
            for (MinuteComment minuteCommentListNewMinuteComment : minuteCommentListNew) {
                if (!minuteCommentListOld.contains(minuteCommentListNewMinuteComment)) {
                    CommitteeMeeting oldMeetingIDOfMinuteCommentListNewMinuteComment = minuteCommentListNewMinuteComment.getMeetingID();
                    minuteCommentListNewMinuteComment.setMeetingID(committeeMeeting);
                    minuteCommentListNewMinuteComment = em.merge(minuteCommentListNewMinuteComment);
                    if (oldMeetingIDOfMinuteCommentListNewMinuteComment != null && !oldMeetingIDOfMinuteCommentListNewMinuteComment.equals(committeeMeeting)) {
                        oldMeetingIDOfMinuteCommentListNewMinuteComment.getMinuteCommentList().remove(minuteCommentListNewMinuteComment);
                        oldMeetingIDOfMinuteCommentListNewMinuteComment = em.merge(oldMeetingIDOfMinuteCommentListNewMinuteComment);
                    }
                }
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
                Long id = committeeMeeting.getMeetingID();
                if (findCommitteeMeeting(id) == null) {
                    throw new NonexistentEntityException("The committeeMeeting with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
                illegalOrphanMessages.add("This CommitteeMeeting (" + committeeMeeting + ") cannot be destroyed since the MinuteComment " + minuteCommentListOrphanCheckMinuteComment + " in its minuteCommentList field has a non-nullable meetingID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Person> personList = committeeMeeting.getPersonList();
            for (Person personListPerson : personList) {
                personListPerson.getCommitteeMeetingList().remove(committeeMeeting);
                personListPerson = em.merge(personListPerson);
            }
            List<Application> applicationList = committeeMeeting.getApplicationList();
            for (Application applicationListApplication : applicationList) {
                applicationListApplication.getCommitteeMeetingList().remove(committeeMeeting);
                applicationListApplication = em.merge(applicationListApplication);
            }
            em.remove(committeeMeeting);
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
            em.close();
        }
    }

    public CommitteeMeeting findCommitteeMeeting(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CommitteeMeeting.class, id);
        } finally {
            em.close();
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
            em.close();
        }
    }
    
    public List<CommitteeMeeting> findAllActiveCommitteeMeetings()
    {
        EntityManager em = getEntityManager();
        
        TypedQuery<CommitteeMeeting> q = em.createQuery("SELECT c FROM CommitteeMeeting c WHERE :curDate BETWEEN c.startDate AND c.endDate", CommitteeMeeting.class).setParameter("curDate", new Date());
        return q.getResultList();
    }
}
