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
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
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
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class, TransactionInterceptor.class})
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class MeetingManagementService implements MeetingManagementServiceLocal {
    //All our ejb should be stateless due to the beter reusablity of the ejb
    //private CommitteeMeeting cMeeting = new CommitteeMeeting();
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        //Create notifications for each attendee
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting creation notification", "Please note that you have been requested to attend a meeting arranged by " + session.getUser().getCompleteName() + "."));
        }
        committeeMeeting.setOrganiser(session.getUser());
        //Create the meeting
        committeeMeeting.setEndDate(null);
        committeeMeetingJpaController.create(committeeMeeting);
        
        //Send notification batch to attendees
        notificationService.sendBatchNotifications(notifications, true);
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
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
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting update notification", "Please note that the following meeting arranged by " + session.getUser().getCompleteName() + " has been updated."));
        }
        
        notificationService.sendBatchNotifications(notifications, true);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void cancelMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        
        CommitteeMeetingJpaController committeeMeetingJpaController = getCommitteeMeetingDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        if(committeeMeeting.getStartDate().before(getGregorianCalendar().getTime()) || committeeMeeting.getMinuteCommentList().size() > 0)
        {
            throw new Exception("Commitee meeting is already active or held");
        }
        
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        
        for(Person p : committeeMeeting.getPersonList())
        {
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting cancelation notification", "Please note that the following meeting arranged by " + session.getUser().getCompleteName() + " has been canceled."));
        }
        
        committeeMeetingJpaController.destroy(committeeMeeting.getMeetingID());
        
        
        
        notificationService.sendBatchNotifications(notifications, true);
        
    }
    
    
    
    //This is a good idea to allow manual start and end aswell

    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws Exception 
    {
        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllMeetings(Session session) throws Exception 
    {        
        return getCommitteeMeetingDAO().findCommitteeMeetingEntities();
    }
    
    /**
     *
     * @param session
     * @return
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllActiveMeetings(Session session) throws Exception 
    {   
        return getCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllActiveMeetingsForWhichUserIsToAttend(Session session) throws Exception 
    {   
        
        List<CommitteeMeeting> committeeMeetings = getCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
        List<CommitteeMeeting> outcommitteeMeetings = new ArrayList<CommitteeMeeting>();
        
        for(CommitteeMeeting committeeMeeting : committeeMeetings)
        {
            if(committeeMeeting.getOrganiser().equals(session.getUser()) )
            {
                outcommitteeMeetings.add(committeeMeeting);
            }
            else if(committeeMeeting.getPersonList().contains(session.getUser()) )
            {
                outcommitteeMeetings.add(committeeMeeting);
            }                
        }
        
        return outcommitteeMeetings;
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllConcludedMeetings(Session session) throws Exception 
    {        
        return getCommitteeMeetingDAO().findAllConcludedCommitteeMeetings();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Person> getAllPostDocCommitteeMembers(Session session) throws Exception 
    {        
        List<Person> persons = getPersonDAO().findUserBySecurityRoleWithAccountStatus(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER, com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
        persons.remove(session.getUser());
        return persons;
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllStillToBeHeldMeetings(Session session) throws Exception 
    {       
        return getCommitteeMeetingDAO().findAllStillToBeHeldCommitteeMeetings();
    }
    
}
