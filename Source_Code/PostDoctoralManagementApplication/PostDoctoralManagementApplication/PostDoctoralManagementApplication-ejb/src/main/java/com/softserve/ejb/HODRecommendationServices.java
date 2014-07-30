/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class HODRecommendationServices implements HODRecommendationServicesLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public HODRecommendationServices() {
    }
    
    public HODRecommendationServices(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     *
     * @return
     */
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected RecommendationReportJpaController getRecommmendationReportDAO()
    {
        return new RecommendationReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AmmendRequestJpaController getAmmendRequestDAO()
    {
        return new AmmendRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    /**
     *
     * @return
     */
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    /**
     *
     * @return
     */
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    /**
     *
     * @return
     */
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }
    
    /**
     *
     * @return
     */
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws java.lang.Exception
     */
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, StartIndex, maxNumberOfRecords);
    }
    
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
    }
    
    /**
     *
     * @param session
     * @param application
     * @param reason
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void declineAppliction(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        applicationServices.declineAppliction(session, application, reason);               
    }
    
    /**
     *
     * @param session
     * @param application
     * @param reason
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void ammendAppliction(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AmmendRequestJpaController ammendRequestJpaController = getAmmendRequestDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        //Ammend application
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);        
        applicationJpaController.edit(application);
        
        AmmendRequest ammendRequest = dBEntitiesFactory.bulidAmmendRequestEntity(application, reason, getGregorianCalendar().getTime());
        ammendRequestJpaController.create(ammendRequest);
        
        //Log action        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Ammendment request for application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        //Send notification to grant holder and applicatantD        
        Notification notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application ammendment requested", "The following application requires ammendment as per request by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);
        
        notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);
    }
    
    /**
     *
     * @param session
     * @param application
     * @param recommendationReport
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void recommendApplication(Session session, Application application, RecommendationReport recommendationReport) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        RecommendationReportJpaController recommendationReportJpaController = getRecommmendationReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        recommendationReportJpaController.create(recommendationReport);
        
        application.setRecommendationReport(recommendationReport);
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        
        try
        {
            applicationJpaController.edit(application);
        }
        catch(Exception ex)
        {
            //If an error occurs during update of application the recommendation report must be removed as well
            recommendationReportJpaController.destroy(recommendationReport.getReportID());
            throw ex;
        }
        
        //Log action
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Application approved" + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        //Send notification to Deans office
        List<Person> DeansOfficeMembers = applicationJpaController.findAllDeansOfficeMembersWhoCanEndorseApplication(application);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : DeansOfficeMembers)
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Application recommended", "The following application has been recommended by " + session.getUser().getCompleteName() + ". Please review for endorsement."));
        }
        notificationService.sendBatchNotifications(notifications, true);
    }
}
