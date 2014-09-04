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
import com.softserve.DBDAO.ResearchFellowInformationJpaController;
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
import com.softserve.DBEntities.ResearchFellowInformation;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
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
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DRISApprovalService implements DRISApprovalServiceLocal {
    
    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
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
    
    protected ResearchFellowInformationJpaController getResearchFellowInformationDAO()
    {
        return new ResearchFellowInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingEndorsedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED,StartIndex,maxNumberOfRecords);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingEndorsedApplications(Session session) throws Exception 
    {        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingEligibleApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, StartIndex, maxNumberOfRecords);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingEligibleApplications(Session session) throws AuthenticationException, Exception 
    {       
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public boolean checkApplicationForEligiblity(Session session, Application application) throws Exception
    {
        Date dobDate = application.getFellow().getCv().getDateOfBirth();
        GregorianCalendar curCal = getGregorianCalendar();
        GregorianCalendar dobCal = getGregorianCalendar();
        dobCal.setTime(dobDate);
        dobCal.add(GregorianCalendar.MONTH, 1);       
        
        
        if((curCal.get(GregorianCalendar.YEAR) - dobCal.get(GregorianCalendar.YEAR) <= 40 && hasPhD(application)) || (hasObtainedPhDInLast5Years(application)))
        {

            return true;
        }
        else
        {
            return false;
        }        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void setApplicationEligibleStatus(Session session, Application application, boolean isElgible) throws Exception
    {        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        EligiblityReportJpaController eligiblityReportJpaController = getEligiblityReportDAO();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        if(application.getEligiblityReport() == null)
        {
            if(isElgible)
            {
                application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
                applicationJpaController.edit(application);
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void denyFunding(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void approveFunding(Session session, Application application, ResearchFellowInformation researchFellowInformation, FundingReport fundingReport, String applicantMessage, Notification cscMesssage, Notification finaceMessage) throws AuthenticationException, RollbackFailureException, Exception
    {        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        FundingReportJpaController fundingReportJpaController = getFundingReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        FundingCostJpaController fundingCostJpaController = getFundingCostDAO();
        
        List<FundingCost> fundingCosts = fundingReport.getFundingCostList();
        
        //Create funding report
        fundingReport.setApplication(application);
        fundingReport.setReportID(application.getApplicationID());
        fundingReport.setDris(session.getUser());
        fundingReport.setTimestamp(getGregorianCalendar().getTime());
        fundingReportJpaController.create(fundingReport);
        
        for(FundingCost fundingCost : fundingCosts)
        {
            fundingCost.setFundingReport(fundingReport);
            fundingCostJpaController.create(fundingCost);
        }
        
        application = applicationJpaController.findApplication(application.getApplicationID());
        
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
        }
        
        if(fellow.getResearchFellowInformation() == null)
        {
            getResearchFellowInformationDAO().create(researchFellowInformation);
        }
        
        fellow.setResearchFellowInformation(researchFellowInformation);
        personJpaController.edit(fellow);

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadFundedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception 
    {
        return getApplicationServicesUTIL().loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, StartIndex, maxNumberOfRecords);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void updateFundingInformation(Session session, Application application) throws Exception 
    {
        FundingReportJpaController fundingReportJpaController = getFundingReportDAO();
        FundingCostJpaController fundingCostJpaController = getFundingCostDAO();
        
        ResearchFellowInformation researchFellowInformation = application.getFellow().getResearchFellowInformation();
        FundingReport fundingReport = application.getFundingReport();       
        
        FundingReport fundingReport1 = fundingReportJpaController.findFundingReport(fundingReport.getReportID());
        List<FundingCost> fundingCosts = fundingReport.getFundingCostList();
        
        for(FundingCost fundingCost : fundingCosts)
        {
            if(!fundingReport1.getFundingCostList().contains(fundingCost))
            {
                fundingCost.setFundingReport(fundingReport);
                fundingCostJpaController.create(fundingCost);
            }
            else
            {
                fundingCostJpaController.edit(fundingCost);
            }
        }
        
        List<FundingCost> toDelete = new ArrayList<FundingCost>();
        for(FundingCost fundingCost : fundingReport1.getFundingCostList())
        {
            if(!fundingCosts.contains(fundingCost))
            {
                toDelete.add(fundingCost);                
            }
        }
        
        for(FundingCost fundingCost : toDelete)
        {
            fundingCostJpaController.destroy(fundingCost.getCostID());
        }
        
        PersonJpaController personJpaController = getPersonDAO();
        Person fellow = personJpaController.findPerson(application.getFellow().getSystemID());
        if(!fellow.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
        {
            fellow.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);            
        }
        
        if(fellow.getResearchFellowInformation() == null)
        {
            getResearchFellowInformationDAO().create(researchFellowInformation);
        }
        
        fellow.setResearchFellowInformation(researchFellowInformation);
        personJpaController.edit(fellow);

    }
    
    
}
