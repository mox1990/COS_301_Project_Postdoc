/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EligiblityReportJpaController;
import com.softserve.DBDAO.FundingCostJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.FundingCost;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
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
    
    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected FundingReportJpaController getFundingReportDAO()
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingCostJpaController getFundingCostDAO()
    {
        return new FundingCostJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EligiblityReportJpaController getEligiblityReportDAO()
    {
        return new EligiblityReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
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
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
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
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
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
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
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
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
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
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        EligiblityReportJpaController eligiblityReportJpaController = getEligiblityReportDAO();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        Date dobDate = application.getFellow().getCv().getDateOfBirth();
        GregorianCalendar curCal = getGregorianCalendar();
        GregorianCalendar dobCal = getGregorianCalendar();
        dobCal.setTime(dobDate);
        dobCal.add(GregorianCalendar.MONTH, 1);       
        
        
        if((curCal.get(GregorianCalendar.YEAR) - dobCal.get(GregorianCalendar.YEAR) <= 40 && hasPhD(application)) || (hasObtainedPhDInLast5Years(application)))
        {

            setApplicationEligibleStatus(session, application, true);
        }
        else
        {
            setApplicationEligibleStatus(session, application, false);
        }
        
    }
    
    @Override
    public void setApplicationEligibleStatus(Session session, Application application, boolean isElgible) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        EligiblityReportJpaController eligiblityReportJpaController = getEligiblityReportDAO();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        if(application.getEligiblityReport() == null)
        {
            if(isElgible)
            {

                application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
                applicationJpaController.edit(application);

                //Log action  
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Application made eligible" + application.getApplicationID(), session.getUser());
                auditTrailService.logAction(auditLog);
            }
            else
            {
                //Send notification to grant holder and applicatantD
                String reason = "Prospective fellow does not meet the eligiblity requirement";

                ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
                applicationServices.declineAppliction(session, application, reason);
            }
            
            EligiblityReport eligiblityReport = dBEntitiesFactory.createEligiblityReportEntity(application, session.getUser(), getGregorianCalendar().getTime());
            eligiblityReportJpaController.create(eligiblityReport);
        }
        else
        {
            throw new Exception("Application already checked for eligiblity.");
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
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        applicationServices.declineAppliction(session, application, reason);   
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
    public void approveFunding(Session session, Application application, FundingReport fundingReport, String applicantMessage, Notification cscMesssage, Notification finaceMessage) throws AuthenticationException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        FundingReportJpaController fundingReportJpaController = getFundingReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        FundingCostJpaController fundingCostJpaController = getFundingCostDAO();
        
        for(FundingCost fundingCost : fundingReport.getFundingCostList())
        {
            fundingCostJpaController.create(fundingCost);
        }
        
        //Create funding report
        fundingReport.setApplication(application);
        fundingReport.setReportID(application.getApplicationID());
        fundingReport.setDris(session.getUser());
        fundingReport.setTimestamp(getGregorianCalendar().getTime());
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
        
        PersonJpaController personJpaController = getPersonDAO();
        Person fellow = personJpaController.findPerson(application.getFellow().getSystemID());
        if(!fellow.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
        {
            fellow.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
            personJpaController.edit(fellow);
        }
                
        //Log action  
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Application approved" + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);

        //Send notification to CSC, Finance, grant holder and applicatant
        ArrayList<Notification> notifications = new ArrayList<Notification>();        
        notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getFellow(), "Application funding approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));
        notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getGrantHolder(), "Application funding approved", "The following application has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));       
        
        notificationService.sendBatchNotifications(notifications, true);
        
        //CSC and finance person
        cscMesssage.setSubject("Application funding approved");
        finaceMessage.setSubject(cscMesssage.getSubject());
        notificationService.sendOnlyEmail(cscMesssage);
        notificationService.sendOnlyEmail(finaceMessage);
        
        
    }
}
