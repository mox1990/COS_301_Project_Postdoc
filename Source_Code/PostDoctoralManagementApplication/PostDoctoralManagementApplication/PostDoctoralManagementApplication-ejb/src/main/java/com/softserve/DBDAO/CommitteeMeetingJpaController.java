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

        Person organiser = committeeMeeting.getOrganiser();
        if (organiser != null) {
            organiser = em.getReference(organiser.getClass(), organiser.getSystemID());
            committeeMeeting.setOrganiser(organiser);
        }
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
        if (organiser != null) {
            organiser.getCommitteeMeetingList1().add(committeeMeeting);
            organiser = em.merge(organiser);
        }
        for (Person personListPerson : committeeMeeting.getPersonList()) {
            personListPerson.getCommitteeMeetingList().add(committeeMeeting);
            personListPerson = em.merge(personListPerson);
        }
        for (Application applicationListApplication : committeeMeeting.getApplicationList()) {
            applicationListApplication.getCommitteeMeetingList().add(committeeMeeting);
            applicationListApplication = em.merge(applicationListApplication);
        }
        for (MinuteComment minuteCommentListMinuteComment : committeeMeeting.getMinuteCommentList()) {
            CommitteeMeeting oldMeetingOfMinuteCommentListMinuteComment = minuteCommentListMinuteComment.getMeeting();
            minuteCommentListMinuteComment.setMeeting(committeeMeeting);
            minuteCommentListMinuteComment = em.merge(minuteCommentListMinuteComment);
            if (oldMeetingOfMinuteCommentListMinuteComment != null) {
                oldMeetingOfMinuteCommentListMinuteComment.getMinuteCommentList().remove(minuteCommentListMinuteComment);
                oldMeetingOfMinuteCommentListMinuteComment = em.merge(oldMeetingOfMinuteCommentListMinuteComment);
            }
        }        
    }
    
    public void edit(CommitteeMeeting committeeMeeting) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        edit(getEntityManager(), committeeMeeting);
    }

    public void edit(EntityManager em, CommitteeMeeting committeeMeeting) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        Long id = committeeMeeting.getMeetingID();
        if (findCommitteeMeeting(id) == null) {
            throw new NonexistentEntityException("The committeeMeeting with id " + id + " no longer exists.");
        }
                
        CommitteeMeeting persistentCommitteeMeeting = em.find(CommitteeMeeting.class, committeeMeeting.getMeetingID());
        Person organiserOld = persistentCommitteeMeeting.getOrganiser();
        Person organiserNew = committeeMeeting.getOrganiser();
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
                illegalOrphanMessages.add("You must retain MinuteComment " + minuteCommentListOldMinuteComment + " since its meeting field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (organiserNew != null) {
            organiserNew = em.getReference(organiserNew.getClass(), organiserNew.getSystemID());
            committeeMeeting.setOrganiser(organiserNew);
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
        if (organiserOld != null && !organiserOld.equals(organiserNew)) {
            organiserOld.getCommitteeMeetingList1().remove(committeeMeeting);
            organiserOld = em.merge(organiserOld);
        }
        if (organiserNew != null && !organiserNew.equals(organiserOld)) {
            organiserNew.getCommitteeMeetingList1().add(committeeMeeting);
            organiserNew = em.merge(organiserNew);
        }
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
                CommitteeMeeting oldMeetingOfMinuteCommentListNewMinuteComment = minuteCommentListNewMinuteComment.getMeeting();
                minuteCommentListNewMinuteComment.setMeeting(committeeMeeting);
                minuteCommentListNewMinuteComment = em.merge(minuteCommentListNewMinuteComment);
                if (oldMeetingOfMinuteCommentListNewMinuteComment != null && !oldMeetingOfMinuteCommentListNewMinuteComment.equals(committeeMeeting)) {
                    oldMeetingOfMinuteCommentListNewMinuteComment.getMinuteCommentList().remove(minuteCommentListNewMinuteComment);
                    oldMeetingOfMinuteCommentListNewMinuteComment = em.merge(oldMeetingOfMinuteCommentListNewMinuteComment);
                }
            }
        }

                

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
        Person organiser = committeeMeeting.getOrganiser();
        if (organiser != null) {
            organiser.getCommitteeMeetingList1().remove(committeeMeeting);
            organiser = em.merge(organiser);
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
