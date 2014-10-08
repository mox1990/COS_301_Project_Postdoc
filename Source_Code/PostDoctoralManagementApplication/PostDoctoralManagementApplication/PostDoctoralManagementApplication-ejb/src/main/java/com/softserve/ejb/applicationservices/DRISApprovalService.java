/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.ResearchFellowInformationJpaController;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.EligiblityReport;
import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxillary.Exceptions.AuthenticationException;
import com.softserve.auxillary.annotations.AuditableMethod;
import com.softserve.auxillary.annotations.SecuredMethod;
import com.softserve.auxillary.interceptors.AuditTrailInterceptor;
import com.softserve.auxillary.interceptors.AuthenticationInterceptor;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
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
    
    @PersistenceUnit(unitName=com.softserve.auxillary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingEndorsedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {        
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(em);        
            return applicationServices.loadPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED,StartIndex,maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingEndorsedApplications(Session session) throws Exception 
    {        
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(em);        
            return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
        finally
        {
            em.close();
        }
    }  
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingEligibleApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {        
        
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(em);        
            return applicationServices.loadPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingEligibleApplications(Session session) throws AuthenticationException, Exception 
    {       
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(em);        
            return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
        }
        finally
        {
            em.close();
        }        
    }
    
    /**
     *
     * @param session
     * @param application
     * @return 
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void setApplicationEligibleStatus(Session session, Application application, boolean isElgible) throws Exception
    {        
        
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            EligiblityReportJpaController eligiblityReportJpaController = dAOFactory.createEligiblityReportDAO();
            List<Notification> notifications = new ArrayList<Notification>();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();

            if(application.getEligiblityReport() == null)
            {
                if(isElgible)
                {
                    application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
                    applicationJpaController.edit(application);
                    
                    notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application is eligible", "Please note that the application '" + application.getProjectTitle() + "' has been found to be eligible for funding consideration for which you are the fellow of. "));
                    notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application is eligible", "Please note that the application '" + application.getProjectTitle() + "' has been found to be eligible for funding consideration for which you are the grant holder of."));                              
                }
                else
                {
                    //Send notification to grant holder and applicatantD
                    String reason = "The fellow does not meet the eligiblity requirement as required by the DRIS.";

                    ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(transactionController.getEntityManager());
                    applicationServices.declineAppliction(session, application, reason); 
                    
                    notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application not eligible", "Please note that the application '" + application.getProjectTitle() + "' has been found to be not eligible for which you are the fellow of. The reason for this is as follows: " + reason));
                    notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application not eligible", "Please note that the application '" + application.getProjectTitle() + "' has been found to be not eligible for which you are the grant holder of. The reason for this is as follows: " + reason));

                }

                EligiblityReport eligiblityReport = dBEntitiesFactory.createEligiblityReportEntity(application, session.getUser(), getGregorianCalendar().getTime());
                eligiblityReportJpaController.create(eligiblityReport);
            }
            else
            {
                throw new Exception("Application already checked for eligiblity.");
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void denyFunding(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(transactionController.getEntityManager());
            applicationServices.declineAppliction(session, application, reason);
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application funding declined", "Please note that the funding for the application '" + application.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application funding declined", "Please note that the funding for the application '" + application.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason));

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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void approveFunding(Session session, Application application, ResearchFellowInformation researchFellowInformation, FundingReport fundingReport, String applicantMessage, Notification cscMesssage, Notification finaceMessage) throws AuthenticationException, RollbackFailureException, Exception
    {   
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            NotificationServiceLocal notificationService = getNotificationServiceEJB();


            FundingCostJpaController fundingCostJpaController = dAOFactory.createFundingCostJpaController();
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            FundingReportJpaController fundingReportJpaController = dAOFactory.createFundingReportDAO();
            PersonJpaController personJpaController = dAOFactory.createPersonDAO();
            ResearchFellowInformationJpaController researchFellowInformationJpaController = dAOFactory.createResearchFellowInformationDAO();

            Application oldApplication  = applicationJpaController.findApplication(application.getApplicationID());


            //Set application status to funded
            oldApplication.setStartDate(application.getStartDate());
            oldApplication.setEndDate(application.getEndDate());
            oldApplication.setFundingReport(null);
            oldApplication.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED);
            applicationJpaController.edit(oldApplication);

            List<FundingCost> fundingCosts = fundingReport.getFundingCostList();

            //Create funding report
            fundingReport.setApplication(application);
            fundingReport.setReportID(application.getApplicationID());
            fundingReport.setDris(session.getUser());
            fundingReport.setTimestamp(getGregorianCalendar().getTime());
            fundingReport.setFundingCostList(null);
            fundingReportJpaController.create(fundingReport);

            for(FundingCost fundingCost : fundingCosts)
            {
                fundingCost.setFundingReport(fundingReport);
                fundingCostJpaController.create(fundingCost);
            }



            Person fellow = personJpaController.findPerson(application.getFellow().getSystemID());
            if(!fellow.getSecurityRoleList().contains(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
            {
                fellow.getSecurityRoleList().add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);            
            }


            researchFellowInformation.setPerson(fellow);
            researchFellowInformation.setSystemAssignedID(fellow.getSystemID());

            if(fellow.getResearchFellowInformation() == null)
            {
                researchFellowInformationJpaController.create(researchFellowInformation);
            }
            else
            {
                researchFellowInformationJpaController.edit(researchFellowInformation);
            }  
            
            //Send notification to CSC, Finance, grant holder and applicatant
            ArrayList<Notification> notifications = new ArrayList<Notification>();        
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getFellow(), "Application funding approved", "The application '" + application.getProjectTitle() + "' has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getGrantHolder(), "Application funding approved", "The application '" + application.getProjectTitle() + "' has been approved for funding by " + session.getUser().getCompleteName() + ". " + applicantMessage));     

            transactionController.CommitTransaction();
            
            cscMesssage.setSubject("Application funding approved");
            finaceMessage.setSubject(cscMesssage.getSubject());
            notificationService.sendOnlyEmail(new Session(session.getHttpSession(),session.getUser(),true),cscMesssage);
            notificationService.sendOnlyEmail(new Session(session.getHttpSession(),session.getUser(),true),finaceMessage);
            
            notificationService.sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadFundedApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception 
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).loadPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void updateFundingInformation(Session session, Application application) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            FundingCostJpaController fundingCostJpaController = dAOFactory.createFundingCostJpaController();
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            FundingReportJpaController fundingReportJpaController = dAOFactory.createFundingReportDAO();
            PersonJpaController personJpaController = dAOFactory.createPersonDAO();
            ResearchFellowInformationJpaController researchFellowInformationJpaController = dAOFactory.createResearchFellowInformationDAO();

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


            Person fellow = personJpaController.findPerson(application.getFellow().getSystemID());
            if(!fellow.getSecurityRoleList().contains(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
            {
                fellow.getSecurityRoleList().add(com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);            
            }

            researchFellowInformation.setPerson(fellow);
            researchFellowInformation.setSystemAssignedID(fellow.getSystemID());

            if(fellow.getResearchFellowInformation() == null)
            {
                researchFellowInformationJpaController.create(researchFellowInformation);
            }
            else
            {
                researchFellowInformationJpaController.edit(researchFellowInformation);
            } 

            Application oldApplication  = applicationJpaController.findApplication(application.getApplicationID());                

            oldApplication.setStartDate(application.getStartDate());
            oldApplication.setEndDate(application.getEndDate());       

            applicationJpaController.edit(oldApplication);

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void terminateApplication(Session session, Application application) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            ApplicationJpaController applicationJpaController = transactionController.getDAOFactoryForTransaction().createApplicationDAO();
            
            Application a = applicationJpaController.findApplication(application.getApplicationID());
            
            a.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED);
            
            applicationJpaController.edit(a);

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public void abandonApplication(Session session, Application application) throws Exception {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            ApplicationJpaController applicationJpaController = transactionController.getDAOFactoryForTransaction().createApplicationDAO();
            
            Application a = applicationJpaController.findApplication(application.getApplicationID());
            
            a.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ABANDONED);
            
            applicationJpaController.edit(a);

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
    
    
    
    
}
