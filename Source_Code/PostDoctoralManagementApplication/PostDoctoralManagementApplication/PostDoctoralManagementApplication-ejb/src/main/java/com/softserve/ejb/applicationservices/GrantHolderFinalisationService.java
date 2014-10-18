/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Address;
import com.softserve.persistence.DBEntities.AmmendRequest;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ApplicationReviewRequest;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.EmployeeInformation;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.Exceptions.CVAlreadExistsException;
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
public class GrantHolderFinalisationService implements GrantHolderFinalisationServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private CVManagementServiceLocal cVManagementServiceLocal;
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected CVManagementServiceLocal getCVManagementServiceEJB()
    {
        return cVManagementServiceLocal;
    }
    
    protected UserAccountManagementServiceLocal getUserAccountManagementServiceEJB()
    {
        return userAccountManagementServiceLocal;
    }
    
    public GrantHolderFinalisationService() {
    }
    
    public GrantHolderFinalisationService(EntityManagerFactory emf) {
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
    
    /**
     *
     * @return
     */
    
    /**
     *
     * @return
     */
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    /**
     *This function is used to create a CV for a grant holder
     * @param session The session which is used to authenticate the user
     * @param cv The CV object containing the cv data to be added
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws NonexistentEntityException If the session user does not exist
     * @throws CVAlreadExistsException If the grant holder already has a CV
     * @throws Exception If an unknown error occurs
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public void createGrantHolderCV(Session session, Cv cv) throws Exception
    {
        
        if(cv == null)
        {
            throw new Exception("CV is not valid");
        }
        
        CVManagementServiceLocal cVManagementService = getCVManagementServiceEJB();
        if(cVManagementService.hasCV(session))
        {
            cVManagementService.updateCV(session, cv);
        }
        else
        {
            cVManagementService.createCV(session, cv);
        }
    }
    
    /**
     *This function loads all the applications that need to finalised by the 
     * specified grant holder
     * @param session The session object used to authenticate the user
     * @param StartIndex
     * @param maxNumberOfRecords
     * @return A list of all the applications that user can finalise
     * @throws com.softserve.Exceptions.AuthenticationException
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {        
        EntityManager em = createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).loadPendingApplications(session.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws Exception
    {        
        EntityManager em = createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).getTotalNumberOfPendingApplications(session.getUser(), com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
        finally
        {
            em.close();
        }
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public void saveChangesToApplication(Session session, Application application) throws Exception
    {   
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createApplicationDAO().edit(application);

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
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
        
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application finalisation declined", "Please note that the finalisation of the application '" + application.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason));

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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
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
            application.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);        
            applicationJpaController.edit(application);

            AmmendRequest ammendRequest = dBEntitiesFactory.createAmmendRequestEntity(application, session.getUser(), reason, getGregorianCalendar().getTime());
            ammendRequestJpaController.create(ammendRequest);
  
            List<Notification> notifications = new ArrayList<Notification>();        
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application ammendment request", "Please note that the grant holder has requested ammendment for the application '" + application.getProjectTitle() + "' for which you are the fellow of. The reason for this is as follows: " + reason));

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
     *This function is used to finalise an applications content and change its 
     * status to finalised.
     * @param session The session object used to authenticate the user
     * @param application The application that needs to be finalised
     * @throws NonexistentEntityException If the application does not exist
     * @throws RollbackFailureException If an error occured while rolling back the entry in the database
     * @throws Exception If an unknown error occured
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public void finaliseApplication(Session session, Application application) throws Exception
    {
        
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();

            application = applicationJpaController.findApplication(application.getApplicationID());

            //Finalise application
            application.setFinalisationDate(getGregorianCalendar().getTime());
            application.setStatus(com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);        
            applicationJpaController.edit(application);

            //Send notification to HOD       
            List<Person> HODs = dAOFactory.createApplicationReviewRequestDAO().findAllPeopleWhoHaveBeenRequestForApplicationAs(application, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD);
            
            for(Person p : HODs)
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application finalised", "The application " + application.getProjectTitle() + " has been finalised by " + session.getUser().getCompleteName() + ". Please review the application for recommendation."));
            }
            
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application finalised", "Please note that the application '" + application.getProjectTitle() + "' has been finalised for which you are the fellow of. "));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application finalised", "Please note that the application '" + application.getProjectTitle() + "' has been finalised for which you are the grant holder of."));              

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public List<Person> getHODsOfApplication(Session session, Application application) throws Exception 
    {        
        EntityManager em = createEntityManager();

        try
        {        
            return getDAOFactory(em).createApplicationDAO().findAllHODsWhoCanRecommendApplication(application);
        }
        finally
        {
            em.close();
        }        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER})
    @AuditableMethod
    @Override
    public void requestSpecificHODtoReview(Session session, Application application, Person hod) throws Exception 
    {     
        EntityManager em = createEntityManager();

        try
        {
            DAOFactory dAOFactory = getDAOFactory(em);
            ApplicationReviewRequestJpaController applicationReviewRequestJpaController = dAOFactory.createApplicationReviewRequestDAO();
            PersonJpaController personJpaController = dAOFactory.createPersonDAO();
            
            List<ApplicationReviewRequest> applicationReviewRequests = applicationReviewRequestJpaController.findAllRequestsThatHaveBeenRequestForApplicationAs(application, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD);
            
            if(applicationReviewRequests != null && applicationReviewRequests.size() > 0)
            {
                for(ApplicationReviewRequest applicationReviewRequest : applicationReviewRequests)
                {
                    applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                }
            }

            application = dAOFactory.createApplicationDAO().findApplication(application.getApplicationID());

            hod.setUpEmployee(true);
            if(hod.getEmployeeInformation() == null)
            {
                hod.setEmployeeInformation(new EmployeeInformation());
                hod.getEmployeeInformation().setPhysicalAddress(new Address());
            }

            if(personJpaController.findUserBySystemIDOrEmail(hod.getSystemID()) == null)
            {

                hod.setSecurityRoleList(new ArrayList<SecurityRole>());
                hod.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
                getUserAccountManagementServiceEJB().generateOnDemandAccount(session, "You have been requested to review a post doctoral fellowship for recommendation consideration.", true, hod);
            }
            else
            {

                hod = personJpaController.findPerson(hod.getSystemID());
                System.out.println(hod.toString() + " " + application.getFellow().toString() + " " + application.getGrantHolder().toString() + " " + application.getGrantHolder().getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD));
                if(!application.getFellow().equals(hod) && (!application.getGrantHolder().equals(hod) || application.getGrantHolder().getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD)) && !application.getPersonList().contains(hod))
                {
                    if(!hod.getSecurityRoleList().contains(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD))
                    {
                        hod.getSecurityRoleList().add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
                        getUserAccountManagementServiceEJB().updateUserAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), hod);
                    }
                }
                else
                {
                    throw new Exception("You cannot, nor the fellow, nor the referees of your application can be requested to review application");
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
            ApplicationReviewRequest applicationReviewRequest = dBEntitiesFactory.createApplicationReviewRequest(application, hod, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD);

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
