/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
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
public class GrantHolderFinalisationService implements GrantHolderFinalisationServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    @EJB
    private CVManagementServiceLocal cVManagementServiceLocal;
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    
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
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected CvJpaController getCVDAO()
    {
        return new CvJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationReviewRequestJpaController getApplicationReviewRequestDAO()
    {
        return new ApplicationReviewRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
     *This function is used to create a CV for a grant holder
     * @param session The session which is used to authenticate the user
     * @param cv The CV object containing the cv data to be added
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws NonexistentEntityException If the session user does not exist
     * @throws CVAlreadExistsException If the grant holder already has a CV
     * @throws Exception If an unknown error occurs
     */
    @Override
    public void createGrantHolderCV(Session session, Cv cv) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
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
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, StartIndex, maxNumberOfRecords);
    }
    
    @Override
    public int countTotalPendingApplications(Session session) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
    }
    
    @Override
    public void saveChangesToApplication(Session session, Application application) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        applicationJpaController.edit(application);
    }
    
    @Override
    public void declineAppliction(Session session, Application application, String reason) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
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
    public void ammendAppliction(Session session, Application application, String reason) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AmmendRequestJpaController ammendRequestJpaController = getAmmendRequestDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        //Ammend application
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);        
        applicationJpaController.edit(application);
        
        AmmendRequest ammendRequest = dBEntitiesFactory.createAmmendRequestEntity(application, session.getUser(), reason, getGregorianCalendar().getTime());
        
        ammendRequestJpaController.create(ammendRequest);
        
        //Log action        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Grant holder ammendment request for application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        //Send notification to grant holder and applicatantD        
        Notification notification = dBEntitiesFactory.createNotificationEntity(session.getUser(), application.getFellow(), "Application ammendment requested", "The following application requires ammendment as per request by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
        notificationService.sendNotification(notification, true);
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
    @Override
    public void finaliseApplication(Session session, Application application) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        application = applicationJpaController.findApplication(application.getApplicationID());
        
        //Finalise application
        application.setFinalisationDate(getGregorianCalendar().getTime());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);        
        applicationJpaController.edit(application);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Finalised application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        //Send notification to HOD       
        List<Person> HODs = getApplicationReviewRequestDAO().findAllPeopleWhoHaveBeenRequestForApplicationAs(application, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TPYE_HOD);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : HODs)
        {
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application finalised", "The following application has been finalised by " + session.getUser().getCompleteName() + ". Please review for endorsement."));
        }
        notificationService.sendBatchNotifications(notifications, true);
        
    }

    @Override
    public List<Person> getHODsOfApplication(Session session, Application application) throws Exception 
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<Person> HODs = applicationJpaController.findAllHODsWhoCanRecommendApplication(application);
        
        return HODs;
    }

    @Override
    public void requestSpecificHODtoReview(Session session, Application application, Person hod) throws Exception 
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        List<Person> requestAlreadyFound = getApplicationReviewRequestDAO().findAllPeopleWhoHaveBeenRequestForApplicationAs(application, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TPYE_HOD);
        
        if(requestAlreadyFound != null & requestAlreadyFound.size() > 0)
        {
            throw new Exception("A request for HOD reviewal has already been made");
        }
        
        if(getPersonDAO().findUserBySystemIDOrEmail(hod.getSystemID()) == null)
        {
            
            hod.setSecurityRoleList(new ArrayList<SecurityRole>());
            hod.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
            getUserAccountManagementServiceEJB().generateOnDemandAccount(session, "You have been requested to review a postdoc fellowship as an HOD", true, hod);
        }
        else
        {
            application = getApplicationDAO().findApplication(application.getApplicationID());
            hod = getPersonDAO().findPerson(hod.getSystemID());
            System.out.println(hod.toString() + " " + application.getFellow().toString() + " " + application.getGrantHolder().toString() + " " + application.getGrantHolder().getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD));
            if(!application.getFellow().equals(hod) && (!application.getGrantHolder().equals(hod) || application.getGrantHolder().getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD)) && !application.getPersonList().contains(hod))
            {
                if(!hod.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD))
                {
                    hod.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
                    getUserAccountManagementServiceEJB().updateUserAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), hod);
                }
            }
            else
            {
                throw new Exception("You cannot, nor the fellow, nor the referees of your application can be requested to review application");
            }
        }
        
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ApplicationReviewRequest applicationReviewRequest = dBEntitiesFactory.createApplicationReviewRequest(application, hod, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TPYE_HOD);
        
        getApplicationReviewRequestDAO().create(applicationReviewRequest);

    }
        
}
