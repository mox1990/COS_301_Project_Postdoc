/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.CommitteeMeetingJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.MinuteCommentJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.MinuteComment;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class MeetingManagementService implements MeetingManagementServiceLocal {
    //All our ejb should be stateless due to the beter reusablity of the ejb
    //private CommitteeMeeting cMeeting = new CommitteeMeeting();
    
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
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
    
    public EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void createMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            
            CommitteeMeetingJpaController committeeMeetingJpaController = dAOFactory.createCommitteeMeetingDAO();

            //Create notifications for each attendee
            for(Person p : committeeMeeting.getPersonList())
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting creation notification", "Please note that you have been requested to attend the meeting '" + committeeMeeting.getName() + "' arranged by " + session.getUser().getCompleteName() + " at '" + committeeMeeting.getVenue() + "' starting at " + committeeMeeting.getStartDate().toString() + "."));
            }
            committeeMeeting.setOrganiser(session.getUser());
            //Create the meeting
            committeeMeeting.setEndDate(null);
            committeeMeetingJpaController.create(committeeMeeting);
            
            transactionController.CommitTransaction();
            
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true);
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
        //Send notification batch to attendees
        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void updateMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception
    {
        
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            CommitteeMeetingJpaController committeeMeetingJpaController = dAOFactory.createCommitteeMeetingDAO();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            ArrayList<Notification> notifications = new ArrayList<Notification>();

            CommitteeMeeting cm = committeeMeetingJpaController.findCommitteeMeeting(committeeMeeting.getMeetingID());

            if(cm.getEndDate() != null || cm.getStartDate().before(getGregorianCalendar().getTime()))
            {
                throw new Exception("Meeting has already started or been concluded");
            }

            committeeMeetingJpaController.edit(committeeMeeting);

            for(Person p : committeeMeeting.getPersonList())
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting update notification", "Please note that you have been requested to attend the meeting '" + committeeMeeting.getName() + "' arranged by " + session.getUser().getCompleteName() + " at '" + committeeMeeting.getVenue() + "' starting at " + committeeMeeting.getStartDate().toString() + " which orginally was the meeting '" + cm.getName() + "' arranged by " + cm.getOrganiser().getCompleteName() + " at '" + cm.getVenue() + "' starting at " + cm.getStartDate().toString() + "."));
            }

            transactionController.CommitTransaction();
            
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true);
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
        
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void cancelMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            CommitteeMeetingJpaController committeeMeetingJpaController = dAOFactory.createCommitteeMeetingDAO();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();

            if(committeeMeeting.getStartDate().before(getGregorianCalendar().getTime()) || committeeMeeting.getMinuteCommentList().size() > 0)
            {
                throw new Exception("Commitee meeting is already active or held");
            }

            ArrayList<Notification> notifications = new ArrayList<Notification>();

            for(Person p : committeeMeeting.getPersonList())
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Postdoc Commitee meeting cancelation notification", "Please note that the following meeting '" + committeeMeeting.getName() + "' arranged by " + session.getUser().getCompleteName() + " has been canceled."));
            }

            committeeMeetingJpaController.destroy(committeeMeeting.getMeetingID());  

            transactionController.CommitTransaction();
            
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true);
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
        
        
    }
    
    
    
    //This is a good idea to allow manual start and end aswell

    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void startMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            if(committeeMeeting.getEndDate() == null)
            {
                committeeMeeting.setStartDate(getGregorianCalendar().getTime());
                dAOFactory.createCommitteeMeetingDAO().edit(committeeMeeting);
            }
            else
            {
                throw new Exception("Meeting has already been concluded");
            }

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
        }
        
        
    }
    
    /**
     *
     * @param session
     * @param committeeMeeting
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void endMeeting(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            if(committeeMeeting.getStartDate().before(getGregorianCalendar().getTime()))
            {
                committeeMeeting.setEndDate(getGregorianCalendar().getTime());
                dAOFactory.createCommitteeMeetingDAO().edit(committeeMeeting);
            }
            else
            {
                throw new Exception("Meeting has not yet started");
            }

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public void addMinuteComment(Session session, MinuteComment minuteComment) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            if(minuteComment.getMeeting().getStartDate().before(getGregorianCalendar().getTime()) && minuteComment.getMeeting().getEndDate() == null)
            {
                minuteComment.setAttendee(session.getUser());
                minuteComment.setTimestamp(getGregorianCalendar().getTime());

                dAOFactory.createMinuteCommentDAO().create(minuteComment);
            }
            else
            {
                throw new Exception("Meeting is not active");
            }

            transactionController.CommitTransaction();
        }
        catch(Exception ex)
        {
            transactionController.RollbackTransaction();
            throw ex;
        }
        finally
        {
            transactionController.CloseEntityManagerForTransaction();
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllMeetings(Session session) throws Exception 
    {        
        EntityManager em = createEntityManager();

        try
        {
            return getDAOFactory(em).createCommitteeMeetingDAO().findCommitteeMeetingEntities();
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    /**
     *
     * @param session
     * @return
     * @throws AuthenticationException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllActiveMeetings(Session session) throws Exception 
    {   
        EntityManager em = createEntityManager();

        try
        {
            return getDAOFactory(em).createCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
        }
        finally
        {
            em.close();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllActiveMeetingsForWhichUserIsToAttend(Session session) throws Exception 
    {   
        EntityManager em = createEntityManager();

        try
        {
            List<CommitteeMeeting> committeeMeetings = getDAOFactory(em).createCommitteeMeetingDAO().findAllActiveCommitteeMeetings();
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
        finally
        {
            em.close();
        }
        
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllConcludedMeetings(Session session) throws Exception 
    {   
        EntityManager em = createEntityManager();

        try
        {
            return getDAOFactory(em).createCommitteeMeetingDAO().findAllConcludedCommitteeMeetings();
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Person> getAllPostDocCommitteeMembers(Session session) throws Exception 
    {        
        EntityManager em = createEntityManager();

        try
        {
            List<Person> persons = getDAOFactory(em).createPersonDAO().findUserBySecurityRoleWithAccountStatus(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER, com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
            persons.remove(session.getUser());
            return persons;
        }
        finally
        {
            em.close();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<CommitteeMeeting> getAllStillToBeHeldMeetings(Session session) throws Exception 
    {       
        EntityManager em = createEntityManager();

        try
        {
            return getDAOFactory(em).createCommitteeMeetingDAO().findAllStillToBeHeldCommitteeMeetings();
        }
        finally
        {
            em.close();
        }
        
    }
    
}
