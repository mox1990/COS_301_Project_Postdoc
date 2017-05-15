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
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class MeetingManagementService implements MeetingManagementServiceLocal {
    //All our ejb should be stateless due to the beter reusablity of the ejb
    //private CommitteeMeeting cMeeting = new CommitteeMeeting();
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected AuditTrailServiceLocal getAuditTrailServiceEJB()
    {
        return auditTrailServiceLocal;
    }
    
    public MeetingManagementService() {
    }
    
    /**
     *
     * @param emf
     */
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
    
    /**
     *
     * @return
     */
    protected MinuteCommentJpaController getMinuteCommentDAO()
    {
        return new MinuteCommentJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    /**
     *
     * @param session
     * @param committeeMeeting
     * @param attendanceList
     * @throws AuthenticationException
     * @throws Exception
     */
    @Override
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        //Create notifications for each attendee
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting creation notification", "Please note that you have been requested to attend a meeting arranged by " + session.getUser().getCompleteName() + "."));
        }
        
        //Create the meeting
        committeeMeeting.setEndDate(null);
        committeeMeetingJpaController.create(committeeMeeting);
        
        //Send notification batch to attendees
        notificationService.sendBatchNotifications(notifications, true);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created a postdoctoral committee meeting", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        CommitteeMeeting cm = committeeMeetingJpaController.findCommitteeMeeting(committeeMeeting.getMeetingID());
        
        if(cm.getEndDate() != null || cm.getStartDate().before(getGregorianCalendar().getTime()))
        {
            throw new Exception("Meeting has already started or been concluded");
        }
        
        committeeMeetingJpaController.edit(committeeMeeting);
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting update notification", "Please note that the following meeting arranged by " + session.getUser().getCompleteName() + " has been updated."));
        }
        
        notificationService.sendBatchNotifications(notifications, true);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated postdoctoral committee meeting", session.getUser());
        auditTrailService.logAction(auditLog);
    }

    @Override
    public void cancelMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        if(committeeMeeting.getStartDate().before(getGregorianCalendar().getTime()) || committeeMeeting.getMinuteCommentList().size() > 0)
        {
            throw new Exception("Commitee meeting is already active or held");
        }
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting cancelation notification", "Please note that the following meeting arranged by " + session.getUser().getCompleteName() + " has been canceled."));
        }
        
        committeeMeetingJpaController.destroy(committeeMeeting.getMeetingID());
        
        
        
        notificationService.sendBatchNotifications(notifications, true);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Updated postdoctoral committee meeting", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    
    
    //This is a good idea to allow manual start and end aswell

    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
        @Override
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception 
    {
        //Authenticate user priviliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        if(committeeMeeting.getEndDate() == null)
        {
            committeeMeeting.setStartDate(getGregorianCalendar().getTime());
            getCommitteeMeetingDAO().edit(committeeMeeting);
        }
        else
        {
            throw new Exception("Meeting has already been concluded");
        }
    }
    
    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
    @Override
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        if(committeeMeeting.getStartDate().before(getGregorianCalendar().getTime()))
        {
            committeeMeeting.setEndDate(getGregorianCalendar().getTime());
            getCommitteeMeetingDAO().edit(committeeMeeting);
        }
        else
        {
            throw new Exception("Meeting has not yet started");
        }
    }
    
    //The changes are to make the function more inline with the specification

    /**
     *
     * @param session
     * @param minuteComment
     * @throws AuthenticationException
     * @throws Exception
     */
        @Override
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        if(minuteComment.getMeeting().getStartDate().before(getGregorianCalendar().getTime()) && minuteComment.getMeeting().getEndDate() == null)
        {
            minuteComment.setAttendee(session.getUser());
            minuteComment.setTimestamp(getGregorianCalendar().getTime());

            getMinuteCommentDAO().create(minuteComment);
        }
        else
        {
            throw new Exception("Meeting is not active");
        }
        
        //This is already handled by the DAO
        //cMeeting.getMinuteCommentList().add(min);
        //getCommitteeMeetingDAO().edit(cMeeting);
    }
    
    /**
     *
     * @param session
     * @return
     * @throws AuthenticationException
     * @throws Exception
     */
    @Override
    public List<CommitteeMeeting> getAllMeetings(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getCommitteeMeetingDAO().findCommitteeMeetingEntities();
    }
    
    /**
     *
     * @param session
     * @return
     * @throws AuthenticationException
     * @throws Exception
     */
    @Override
    public List<CommitteeMeeting> getAllActiveMeetings(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
    }

    @Override
    public List<CommitteeMeeting> getAllConcludedMeetings(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getCommitteeMeetingDAO().findAllConcludedCommitteeMeetings();
    }

    @Override
    public List<Person> getAllPostDocCommitteeMembers(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getPersonDAO().findUserBySecurityRoleWithAccountStatus(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER, com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
    }

    @Override
    public List<CommitteeMeeting> getAllStillToBeHeldMeetings(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getCommitteeMeetingDAO().findAllStillToBeHeldCommitteeMeetings();
    }
    
}
