/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.DeclineReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ApplicationReviewRequest;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.DeclineReport;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
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
public class HODRecommendationServices implements HODRecommendationServicesLocal {

    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }

    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
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
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws java.lang.Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).loadPendingApplications(session.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws Exception
    { 
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).getTotalNumberOfPendingApplications(session.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
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
     * @param reason
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void declineAppliction(Session session, Application application, String reason) throws Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(transactionController.StartTransaction());
            applicationServices.declineAppliction(session, application, reason);
            
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application recommendation declined", "Please note that the recommendation for the application '" + application.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application recommendation declined", "Please note that the recommendation for the application '" + application.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason));

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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void ammendAppliction(Session session, Application application, String reason) throws Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            AmmendRequestJpaController ammendRequestJpaController = dAOFactory.createAmmendRequestDAO();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();

            //Ammend application
            application.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);        
            applicationJpaController.edit(application);

            AmmendRequest ammendRequest = dBEntitiesFactory.createAmmendRequestEntity(application, session.getUser(), reason, getGregorianCalendar().getTime());
            ammendRequestJpaController.create(ammendRequest);
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application ammendment request", "Please note that the HOD has requested ammendment for the application '" + application.getProjectTitle() + "' for which you are the grant holder of. The reason for this is as follows: " + reason));

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
     * @param recommendationReport
     * @throws AuthenticationException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void recommendApplication(Session session, Application application, RecommendationReport recommendationReport) throws Exception
    {
        
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            RecommendationReportJpaController recommendationReportJpaController = dAOFactory.createRecommendationReportDAO();
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            

            recommendationReport.setReportID(application.getApplicationID());
            recommendationReport.setHod(session.getUser());
            recommendationReport.setTimestamp(getGregorianCalendar().getTime());
            recommendationReport.setApplication(application);
            recommendationReportJpaController.create(recommendationReport);

            application = applicationJpaController.findApplication(application.getApplicationID());

            application.setRecommendationReport(recommendationReport);
            application.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);

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
            
            for(Person p : DeansOfficeMembers)
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application recommended", "The application " + application.getProjectTitle() + " has been recommend by " + session.getUser().getCompleteName() + ". Please review application for endorsement."));
            }
            
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application recommended", "Please note that the application '" + application.getProjectTitle() + "' has been recommended for which you are the fellow of. "));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application recommended", "Please note that the application '" + application.getProjectTitle() + "' has been recommended for which you are the grant holder of."));        

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public List<Person> getDeansOfApplication(Session session, Application application) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createApplicationDAO().findAllDeansOfficeMembersWhoCanEndorseApplication(application);
        }
        finally
        {
            em.close();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD})
    @AuditableMethod
    @Override
    public void requestSpecificDeanToReview(Session session, Application application, Person dean) throws Exception
    {        
        EntityManager em = emf.createEntityManager();

        try
        {
            DAOFactory dAOFactory = getDAOFactory(em);
            ApplicationReviewRequestJpaController applicationReviewRequestJpaController = dAOFactory.createApplicationReviewRequestDAO();
            PersonJpaController personJpaController = dAOFactory.createPersonDAO();
            List<ApplicationReviewRequest> applicationReviewRequests = applicationReviewRequestJpaController.findAllRequestsThatHaveBeenRequestForApplicationAs(application, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);

            if(applicationReviewRequests != null && applicationReviewRequests.size() > 0)
            {
                for(ApplicationReviewRequest applicationReviewRequest : applicationReviewRequests)
                {
                    applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                }
            }

            application = dAOFactory.createApplicationDAO().findApplication(application.getApplicationID());

            dean.setUpEmployee(true);
            if(dean.getEmployeeInformation() == null)
            {
                dean.setEmployeeInformation(new EmployeeInformation());
                dean.getEmployeeInformation().setPhysicalAddress(new Address());
            }
            if(personJpaController.findUserBySystemIDOrEmail(dean.getSystemID()) == null)
            {
                dean.setSecurityRoleList(new ArrayList<SecurityRole>());
                dean.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
                getUserAccountManagementServiceEJB().generateOnDemandAccount(session, "You have been requested to review a post doctoral fellowship for endorsement consideration", true, dean);
            }
            else
            {

                dean = personJpaController.findPerson(dean.getSystemID());

                if(!session.getUser().equals(dean) && !application.getFellow().equals(dean) && (!application.getGrantHolder().equals(dean) || application.getGrantHolder().getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER)) && !application.getPersonList().contains(dean))
                {
                    if(!dean.getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER))
                    {
                        dean.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
                        getUserAccountManagementServiceEJB().updateUserAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), dean);
                    }
                }
                else
                {
                    throw new Exception("You cannot, nor the fellow, nor the grant holder, nor the referees of your application can be requested to review the application for endorsement");
                }
            }
        }
        finally
        {
            em.close();
        }
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            ApplicationReviewRequest applicationReviewRequest = dBEntitiesFactory.createApplicationReviewRequest(application, dean, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);

            dAOFactory.createApplicationReviewRequestDAO().create(applicationReviewRequest);

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
