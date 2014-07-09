/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.CommitteeMeetingJpaController;
import com.softserve.DBDAO.MinuteCommentJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.constants.PersistenceConstants;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateful
public class MeetingManagementService implements MeetingManagementServiceLocal {
    //All our ejb should be stateless due to the beter reusablity of the ejb
    //private CommitteeMeeting cMeeting = new CommitteeMeeting();
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public MeetingManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of PersonJpaController
     */
    protected CommitteeMeetingJpaController getCommitteeMeetingDAO()
    {
        return new CommitteeMeetingJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected MinuteCommentJpaController getMinuteCommentDAO()
    {
        return new MinuteCommentJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService();
    }
    
    @Override
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting, List<Person> attendanceList) throws Exception
    {
        //AuthenticUser(session, list of privliges)
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        for(Person p : attendanceList)
        {
            committeeMeeting.getPersonList().add(p);
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting notification", "Please note that you have been requested to attend a meeting arranged by " + session.getUser().getCompleteName() + "."));
        }
        
        committeeMeetingJpaController.create(committeeMeeting);
        
        notificationService.sendBatchNotifications(notifications, true);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created a postdoctoral committee meeting", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        committeeMeetingJpaController.edit(committeeMeeting);
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting notification", "Please note that the following meeting arranged by " + session.getUser().getCompleteName() + " has been updated."));
        }
        
        notificationService.sendBatchNotifications(notifications, true);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated postdoctoral committee meeting", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    //This is a good idea to allow manual start and end aswell
    @Override
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        //AuthenticUser(session, list of privliges)
        
        committeeMeeting.setStartDate(new Timestamp(new Date().getTime()));
        getCommitteeMeetingDAO().edit(committeeMeeting);
    }
    
    @Override
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        //AuthenticUser(session, list of privliges)
        
        committeeMeeting.setEndDate(new Timestamp(new Date().getTime()));
        getCommitteeMeetingDAO().edit(committeeMeeting);
    }
    
    //The changes are to make the function more inline with the specification
    @Override
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws Exception 
    {
        //aLog.logAction(sess.getUser(), comment);
        
        minuteComment.setAttendeeID(session.getUser());
        
        getMinuteCommentDAO().create(minuteComment);
        
        //This is already handled by the DAO
        //cMeeting.getMinuteCommentList().add(min);
        //getCommitteeMeetingDAO().edit(cMeeting);
    }
    
    @Override
    public List<CommitteeMeeting> getAllMeetings(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        return getCommitteeMeetingDAO().findCommitteeMeetingEntities();
    }
    
    @Override
    public List<CommitteeMeeting> getAllActiveMeetings(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        return getCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
    }
    
    //A committee meeting should not be deleted from the database when a meeting ends
    /*@Override
    public CommitteeMeeting endMeeting(Session sess) throws Exception
    {
        cMeeting.setEndDate(new Timestamp(new Date().getTime()));
        
        getCommitteeMeetingDAO().edit(cMeeting);
        
        getCommitteeMeetingDAO().destroy(cMeeting.getMeetingID());
        
        cMeeting.setMeetingID(null);
        cMeeting.setEndDate(null);
        cMeeting.setStartDate(null);
        
        return cMeeting;
    }*/
    /* These functions are handled by the managed bean. The update function will handle this mainly
    @Override
    public CommitteeMeeting addEndorsedApplication(Session sess) throws Exception
    {        
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        EntityManager em = emf.createEntityManager();
        
        List<Application> a = em.createNamedQuery("Applications.findByType", Application.class).setParameter("type", PersistenceConstants.APPLICATION_TYPE_NEW).getResultList();
        cMeeting.getApplicationList().addAll(a);
        
        getCommitteeMeetingDAO().edit(cMeeting);
        
        addMinuteComment(sess, sess.getUser().getTitle() + " " + sess.getUser().getSurname() + " added endorsed Applications to the meeting(" + cMeeting.toString() +").");
        
        return cMeeting;
    }
    
    @Override
    public CommitteeMeeting addEndorsedRenewals(Session sess) throws Exception
    {
        // TODO: Fix implementation
        EntityManager em = emf.createEntityManager();
        
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        List<Application> a = em.createNamedQuery("Applications.findByType", Application.class).setParameter("type", PersistenceConstants.APPLICATION_TYPE_RENEWAL).getResultList();
        cMeeting.getApplicationList().addAll(a);
        
        getCommitteeMeetingDAO().edit(cMeeting);
        
        addMinuteComment(sess, sess.getUser().getTitle() + " " + sess.getUser().getSurname() + " added endorsed renewals to the meeting(" + cMeeting.toString() + ").");
        
        return cMeeting;
    }
    
    @Override
    public CommitteeMeeting addMemberToMeeting(Session sess) throws Exception
    {
        // TODO: Fix implementation 
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        if(sess.getUser().getSecurityRoleList().contains(PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER)
                && !cMeeting.getPersonList().contains(sess.getUser()))
        {
            cMeeting.getPersonList().add(sess.getUser());
            getCommitteeMeetingDAO().edit(cMeeting);
        }
        
        addMinuteComment(sess, sess.getUser().getTitle() + " " + sess.getUser().getSurname() + " entered meeting");
        
        return cMeeting;
    }
    
    @Override
    public CommitteeMeeting removeMemberFromMeeting(Session sess) throws Exception
    {
        if(cMeeting.getMeetingID() == null)
            throw new Exception("Meeting has not been started.");
        
        addMinuteComment(sess, sess.getUser().getTitle() + " " + sess.getUser().getSurname() + " left meeting");
        
        if(cMeeting.getPersonList().contains(sess.getUser()))
        {
            cMeeting.getPersonList().add(sess.getUser());
            getCommitteeMeetingDAO().edit(cMeeting);
        }
        
        return cMeeting;
    }
    
    @Override
    public List<Person> getAttendants()
    {
        return cMeeting.getPersonList();
    }
    
    @Override
    public List<Application> getApplications()
    {
        return cMeeting.getApplicationList();
    }
    */
    
}
