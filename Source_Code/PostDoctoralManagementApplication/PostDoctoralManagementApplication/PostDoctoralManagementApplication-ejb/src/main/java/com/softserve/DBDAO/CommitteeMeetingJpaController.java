/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
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
import com.softserve.DBEnties.Person;
import java.util.ArrayList;
import java.util.Collection;
import com.softserve.DBEnties.Application;
import com.softserve.DBEnties.CommitteeMeeting;
import com.softserve.DBEnties.MinuteComment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        if (committeeMeeting.getPersonCollection() == null) {
            committeeMeeting.setPersonCollection(new ArrayList<Person>());
        }
        if (committeeMeeting.getApplicationCollection() == null) {
            committeeMeeting.setApplicationCollection(new ArrayList<Application>());
        }
        if (committeeMeeting.getMinuteCommentCollection() == null) {
            committeeMeeting.setMinuteCommentCollection(new ArrayList<MinuteComment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Person> attachedPersonCollection = new ArrayList<Person>();
            for (Person personCollectionPersonToAttach : committeeMeeting.getPersonCollection()) {
                personCollectionPersonToAttach = em.getReference(personCollectionPersonToAttach.getClass(), personCollectionPersonToAttach.getSystemID());
                attachedPersonCollection.add(personCollectionPersonToAttach);
            }
            committeeMeeting.setPersonCollection(attachedPersonCollection);
            Collection<Application> attachedApplicationCollection = new ArrayList<Application>();
            for (Application applicationCollectionApplicationToAttach : committeeMeeting.getApplicationCollection()) {
                applicationCollectionApplicationToAttach = em.getReference(applicationCollectionApplicationToAttach.getClass(), applicationCollectionApplicationToAttach.getApplicationID());
                attachedApplicationCollection.add(applicationCollectionApplicationToAttach);
            }
            committeeMeeting.setApplicationCollection(attachedApplicationCollection);
            Collection<MinuteComment> attachedMinuteCommentCollection = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentCollectionMinuteCommentToAttach : committeeMeeting.getMinuteCommentCollection()) {
                minuteCommentCollectionMinuteCommentToAttach = em.getReference(minuteCommentCollectionMinuteCommentToAttach.getClass(), minuteCommentCollectionMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentCollection.add(minuteCommentCollectionMinuteCommentToAttach);
            }
            committeeMeeting.setMinuteCommentCollection(attachedMinuteCommentCollection);
            em.persist(committeeMeeting);
            for (Person personCollectionPerson : committeeMeeting.getPersonCollection()) {
                personCollectionPerson.getCommitteeMeetingCollection().add(committeeMeeting);
                personCollectionPerson = em.merge(personCollectionPerson);
            }
            for (Application applicationCollectionApplication : committeeMeeting.getApplicationCollection()) {
                applicationCollectionApplication.getCommitteeMeetingCollection().add(committeeMeeting);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
            }
            for (MinuteComment minuteCommentCollectionMinuteComment : committeeMeeting.getMinuteCommentCollection()) {
                CommitteeMeeting oldMeetingIDOfMinuteCommentCollectionMinuteComment = minuteCommentCollectionMinuteComment.getMeetingID();
                minuteCommentCollectionMinuteComment.setMeetingID(committeeMeeting);
                minuteCommentCollectionMinuteComment = em.merge(minuteCommentCollectionMinuteComment);
                if (oldMeetingIDOfMinuteCommentCollectionMinuteComment != null) {
                    oldMeetingIDOfMinuteCommentCollectionMinuteComment.getMinuteCommentCollection().remove(minuteCommentCollectionMinuteComment);
                    oldMeetingIDOfMinuteCommentCollectionMinuteComment = em.merge(oldMeetingIDOfMinuteCommentCollectionMinuteComment);
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
            Collection<Person> personCollectionOld = persistentCommitteeMeeting.getPersonCollection();
            Collection<Person> personCollectionNew = committeeMeeting.getPersonCollection();
            Collection<Application> applicationCollectionOld = persistentCommitteeMeeting.getApplicationCollection();
            Collection<Application> applicationCollectionNew = committeeMeeting.getApplicationCollection();
            Collection<MinuteComment> minuteCommentCollectionOld = persistentCommitteeMeeting.getMinuteCommentCollection();
            Collection<MinuteComment> minuteCommentCollectionNew = committeeMeeting.getMinuteCommentCollection();
            List<String> illegalOrphanMessages = null;
            for (MinuteComment minuteCommentCollectionOldMinuteComment : minuteCommentCollectionOld) {
                if (!minuteCommentCollectionNew.contains(minuteCommentCollectionOldMinuteComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentCollectionOldMinuteComment + " since its meetingID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Person> attachedPersonCollectionNew = new ArrayList<Person>();
            for (Person personCollectionNewPersonToAttach : personCollectionNew) {
                personCollectionNewPersonToAttach = em.getReference(personCollectionNewPersonToAttach.getClass(), personCollectionNewPersonToAttach.getSystemID());
                attachedPersonCollectionNew.add(personCollectionNewPersonToAttach);
            }
            personCollectionNew = attachedPersonCollectionNew;
            committeeMeeting.setPersonCollection(personCollectionNew);
            Collection<Application> attachedApplicationCollectionNew = new ArrayList<Application>();
            for (Application applicationCollectionNewApplicationToAttach : applicationCollectionNew) {
                applicationCollectionNewApplicationToAttach = em.getReference(applicationCollectionNewApplicationToAttach.getClass(), applicationCollectionNewApplicationToAttach.getApplicationID());
                attachedApplicationCollectionNew.add(applicationCollectionNewApplicationToAttach);
            }
            applicationCollectionNew = attachedApplicationCollectionNew;
            committeeMeeting.setApplicationCollection(applicationCollectionNew);
            Collection<MinuteComment> attachedMinuteCommentCollectionNew = new ArrayList<MinuteComment>();
            for (MinuteComment minuteCommentCollectionNewMinuteCommentToAttach : minuteCommentCollectionNew) {
                minuteCommentCollectionNewMinuteCommentToAttach = em.getReference(minuteCommentCollectionNewMinuteCommentToAttach.getClass(), minuteCommentCollectionNewMinuteCommentToAttach.getCommentID());
                attachedMinuteCommentCollectionNew.add(minuteCommentCollectionNewMinuteCommentToAttach);
            }
            minuteCommentCollectionNew = attachedMinuteCommentCollectionNew;
            committeeMeeting.setMinuteCommentCollection(minuteCommentCollectionNew);
            committeeMeeting = em.merge(committeeMeeting);
            for (Person personCollectionOldPerson : personCollectionOld) {
                if (!personCollectionNew.contains(personCollectionOldPerson)) {
                    personCollectionOldPerson.getCommitteeMeetingCollection().remove(committeeMeeting);
                    personCollectionOldPerson = em.merge(personCollectionOldPerson);
                }
            }
            for (Person personCollectionNewPerson : personCollectionNew) {
                if (!personCollectionOld.contains(personCollectionNewPerson)) {
                    personCollectionNewPerson.getCommitteeMeetingCollection().add(committeeMeeting);
                    personCollectionNewPerson = em.merge(personCollectionNewPerson);
                }
            }
            for (Application applicationCollectionOldApplication : applicationCollectionOld) {
                if (!applicationCollectionNew.contains(applicationCollectionOldApplication)) {
                    applicationCollectionOldApplication.getCommitteeMeetingCollection().remove(committeeMeeting);
                    applicationCollectionOldApplication = em.merge(applicationCollectionOldApplication);
                }
            }
            for (Application applicationCollectionNewApplication : applicationCollectionNew) {
                if (!applicationCollectionOld.contains(applicationCollectionNewApplication)) {
                    applicationCollectionNewApplication.getCommitteeMeetingCollection().add(committeeMeeting);
                    applicationCollectionNewApplication = em.merge(applicationCollectionNewApplication);
                }
            }
            for (MinuteComment minuteCommentCollectionNewMinuteComment : minuteCommentCollectionNew) {
                if (!minuteCommentCollectionOld.contains(minuteCommentCollectionNewMinuteComment)) {
                    CommitteeMeeting oldMeetingIDOfMinuteCommentCollectionNewMinuteComment = minuteCommentCollectionNewMinuteComment.getMeetingID();
                    minuteCommentCollectionNewMinuteComment.setMeetingID(committeeMeeting);
                    minuteCommentCollectionNewMinuteComment = em.merge(minuteCommentCollectionNewMinuteComment);
                    if (oldMeetingIDOfMinuteCommentCollectionNewMinuteComment != null && !oldMeetingIDOfMinuteCommentCollectionNewMinuteComment.equals(committeeMeeting)) {
                        oldMeetingIDOfMinuteCommentCollectionNewMinuteComment.getMinuteCommentCollection().remove(minuteCommentCollectionNewMinuteComment);
                        oldMeetingIDOfMinuteCommentCollectionNewMinuteComment = em.merge(oldMeetingIDOfMinuteCommentCollectionNewMinuteComment);
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
            Collection<MinuteComment> minuteCommentCollectionOrphanCheck = committeeMeeting.getMinuteCommentCollection();
            for (MinuteComment minuteCommentCollectionOrphanCheckMinuteComment : minuteCommentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CommitteeMeeting (" + committeeMeeting + ") cannot be destroyed since the MinuteComment " + minuteCommentCollectionOrphanCheckMinuteComment + " in its minuteCommentCollection field has a non-nullable meetingID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Person> personCollection = committeeMeeting.getPersonCollection();
            for (Person personCollectionPerson : personCollection) {
                personCollectionPerson.getCommitteeMeetingCollection().remove(committeeMeeting);
                personCollectionPerson = em.merge(personCollectionPerson);
            }
            Collection<Application> applicationCollection = committeeMeeting.getApplicationCollection();
            for (Application applicationCollectionApplication : applicationCollection) {
                applicationCollectionApplication.getCommitteeMeetingCollection().remove(committeeMeeting);
                applicationCollectionApplication = em.merge(applicationCollectionApplication);
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
    
}
