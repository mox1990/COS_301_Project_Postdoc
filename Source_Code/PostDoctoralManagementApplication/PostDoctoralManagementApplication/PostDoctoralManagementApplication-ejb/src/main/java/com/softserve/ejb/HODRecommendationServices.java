/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
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
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class, TransactionInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class HODRecommendationServices implements HODRecommendationServicesLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected UserAccountManagementServiceLocal getUserAccountManagementServiceEJB()
    {
        return userAccountManagementServiceLocal;
    }

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
    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationReviewRequestJpaController getApplicationReviewRequestDAO()
    {
        return new ApplicationReviewRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, StartIndex, maxNumberOfRecords);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws Exception
    { 
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void declineAppliction(Session session, Application application, String reason) throws Exception
    {        
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void ammendAppliction(Session session, Application application, String reason) throws Exception
    {        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AmmendRequestJpaController ammendRequestJpaController = getAmmendRequestDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        //Ammend application
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);        
        applicationJpaController.edit(application);
        
        AmmendRequest ammendRequest = dBEntitiesFactory.createAmmendRequestEntity(application, session.getUser(), reason, getGregorianCalendar().getTime());
        ammendRequestJpaController.create(ammendRequest);
        
        //Send notification to grant holder and applicatantD        
        Notification notification = dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getFellow(), "Application ammendment requested", "The following application requires ammendment as per request by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);
        
        notification = dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getGrantHolder(), "Application ammendment requested", "The following application requires ammendment as per request by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void recommendApplication(Session session, Application application, RecommendationReport recommendationReport) throws Exception
    {
       
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        RecommendationReportJpaController recommendationReportJpaController = getRecommmendationReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        recommendationReport.setReportID(application.getApplicationID());
        recommendationReport.setHod(session.getUser());
        recommendationReport.setTimestamp(getGregorianCalendar().getTime());
        recommendationReport.setApplication(application);
        recommendationReportJpaController.create(recommendationReport);
        
        application = applicationJpaController.findApplication(application.getApplicationID());
        
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
        
        //Send notification to Deans office
        List<Person> DeansOfficeMembers = applicationJpaController.findAllDeansOfficeMembersWhoCanEndorseApplication(application);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : DeansOfficeMembers)
        {
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application recommended", "The following application has been recommended by " + session.getUser().getCompleteName() + ". Please review for endorsement."));
        }
        notificationService.sendBatchNotifications(notifications, true);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public List<Person> getDeansOfApplication(Session session, Application application) throws Exception
    {
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<Person> Deans = applicationJpaController.findAllDeansOfficeMembersWhoCanEndorseApplication(application);
        
        return Deans;
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void requestSpecificDeanToReview(Session session, Application application, Person dean) throws Exception
    {        
        List<ApplicationReviewRequest> applicationReviewRequests = getApplicationReviewRequestDAO().findAllRequestsThatHaveBeenRequestForApplicationAs(application, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);
        ApplicationReviewRequestJpaController applicationReviewRequestJpaController = getApplicationReviewRequestDAO();
        if(applicationReviewRequests != null && applicationReviewRequests.size() > 0)
        {
            for(ApplicationReviewRequest applicationReviewRequest : applicationReviewRequests)
            {
                applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
            }
        }
        
        application = getApplicationDAO().findApplication(application.getApplicationID());
        
        dean.setUpEmployee(true);
        if(dean.getEmployeeInformation() == null)
        {
            dean.setEmployeeInformation(new EmployeeInformation());
            dean.getEmployeeInformation().setPhysicalAddress(new Address());
        }
        if(getPersonDAO().findUserBySystemIDOrEmail(dean.getSystemID()) == null)
        {
            
            dean.setSecurityRoleList(new ArrayList<SecurityRole>());
            dean.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            getUserAccountManagementServiceEJB().generateOnDemandAccount(session, "You have been requested to review a postdoc fellowship as an Dean", true, dean);
        }
        else
        {
            
            dean = getPersonDAO().findPerson(dean.getSystemID());
            
            if(!session.getUser().equals(dean) && !application.getFellow().equals(dean) && (!application.getGrantHolder().equals(dean) || application.getGrantHolder().getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER)) && !application.getPersonList().contains(dean))
            {
                if(!dean.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER))
                {
                    dean.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
                    getUserAccountManagementServiceEJB().updateUserAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), dean);
                }
            }
            else
            {
                throw new Exception("You cannot, nor the fellow, nor the referees of your application can be requested to review application");
            }
        }
        
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ApplicationReviewRequest applicationReviewRequest = dBEntitiesFactory.createApplicationReviewRequest(application, dean, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);
        
        applicationReviewRequestJpaController.create(applicationReviewRequest);
    }
}
