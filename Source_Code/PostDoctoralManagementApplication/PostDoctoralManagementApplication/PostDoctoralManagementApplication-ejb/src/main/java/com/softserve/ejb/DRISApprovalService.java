/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
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
public class DRISApprovalService implements DRISApprovalServiceLocal {
    
    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public DRISApprovalService() {
    }

    public DRISApprovalService(EntityManagerFactory emf) {
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
    protected FundingReportJpaController getFundingReportDAO()
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
    
    protected ApplicationServices getApplicationServicesUTIL()
    {
        return new ApplicationServices(emf);
    }
    
    protected boolean hasPhD(Application application)
    {
        List<AcademicQualification> aqList = application.getFellow().getCv().getAcademicQualificationList();
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean hasObtainedPhDInLast5Years(Application application)
    {     
        
        List<AcademicQualification> aqList = application.getFellow().getCv().getAcademicQualificationList();
        
        GregorianCalendar curCal = new GregorianCalendar();
        GregorianCalendar obtainCal = new GregorianCalendar();
        
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                obtainCal.setTimeInMillis(aq.getYearObtained().getTime());
                obtainCal.set(aq.getYearObtained().getYear(), aq.getYearObtained().getMonth() + 1, aq.getYearObtained().getDay());
                if(curCal.get(GregorianCalendar.YEAR) - obtainCal.get(GregorianCalendar.YEAR) <= 5)
                {
                    return true;
                }
            }
        }
        
        return false;
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
    public List<Application> loadPendingEndorsedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED,StartIndex,maxNumberOfRecords);
    }

    @Override
    public int countTotalPendingEndorsedApplications(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
    }
    
    
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     */
    
    @Override
    public List<Application> loadPendingEligibleApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, StartIndex, maxNumberOfRecords);
    }

    @Override
    public int countTotalPendingEligibleApplications(Session session) throws AuthenticationException, Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
    }
    
    /**
     *
     * @param session
     * @param application
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void checkApplicationForEligiblity(Session session, Application application) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        Date dobDate = application.getFellow().getCv().getDateOfBirth();
        GregorianCalendar curCal = new GregorianCalendar();
        GregorianCalendar dobCal = new GregorianCalendar();
        dobCal.set(dobDate.getYear(), dobDate.getMonth() + 1, dobDate.getDay());
        
        application.setEligiblityCheckDate(new Date());
        if((curCal.get(GregorianCalendar.YEAR) - dobCal.get(GregorianCalendar.YEAR) <= 40 && hasPhD(application)) || (hasObtainedPhDInLast5Years(application)))
        {
            
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
            applicationJpaController.edit(application);
            
            //Log action  
            AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Application made eligible" + application.getApplicationID(), session.getUser());
            auditTrailService.logAction(auditLog);
        }
        else
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);
            applicationJpaController.edit(application);
            
            //Log action  
            AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Declined application " + application.getApplicationID(), session.getUser());
            auditTrailService.logAction(auditLog);
        
            //Send notification to grant holder and applicatantD
            String reason = "Prospective fellow does not meet the eligiblity requirement";
            Notification notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
            notificationService.sendNotification(notification, true);
        
            notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolderID(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
            notificationService.sendNotification(notification, true);
        }
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
    public void denyFunding(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        //Set application status to declined
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);
        applicationJpaController.edit(application);
        
        //Log action  
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Declined application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);

        //Send notification to grant holder and applicatantD
        Notification notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);

        notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolderID(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);
    }
    
    /**
     *
     * @param session
     * @param application
     * @param fundingReport
     * @param applicantMessage
     * @param cscMesssage
     * @param finaceMessage
     * @throws AuthenticationException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void approveFunding(Session session, Application application, FundingReport fundingReport, String applicantMessage, String cscMesssage, String finaceMessage) throws AuthenticationException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        FundingReportJpaController fundingReportJpaController = getFundingReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        //Create funding report
        fundingReportJpaController.create(fundingReport);
        
        //Set application status to funded
        application.setFundingReport(fundingReport);
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED);
        
        try
        {
            applicationJpaController.edit(application);
        }
        catch(Exception ex)
        {
            //If an error occurs during update of application the recommendation report must be removed as well
            fundingReportJpaController.destroy(fundingReport.getReportID());
            throw ex;
        }
                
        //Log action  
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Application approved" + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);

        //Send notification to CSC, Finance, grant holder and applicatant
        ArrayList<Notification> notifications = new ArrayList<Notification>();        
        notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));
        notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolderID(), "Application approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));       
        //CSC and finance person
        notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + cscMesssage));
        notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolderID(), "Application approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + finaceMessage));
        notificationService.sendBatchNotifications(notifications, true);
        
        
    }
}
