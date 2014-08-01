/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Date;
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
public class GrantHolderFinalisationService implements GrantHolderFinalisationServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

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
    protected CVManagementService getCVManagementServiceEJB()
    {
        return new CVManagementService(emf);
    }
    
    /**
     *
     * @return
     */
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf);
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
    public void createGrantHolderCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        if(cv == null)
        {
            throw new Exception("CV is not valid");
        }
        
        CVManagementService cVManagementService = getCVManagementServiceEJB();
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
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED, StartIndex, maxNumberOfRecords);
    }
    
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
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
    public void finaliseApplication(Session session, Application application) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        //Finalise application
        application.setFinalisationDate(new Date());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);        
        applicationJpaController.edit(application);
        
        //Log action      
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Finalised application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        //Send notification to HOD        
        List<Person> HODs = applicationJpaController.findAllHODsWhoCanRecommendApplication(application);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : HODs)
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Application finalised", "The following application has been finalised by " + session.getUser().getCompleteName() + ". Please review for endorsement."));
        }
        notificationService.sendBatchNotifications(notifications, true);
        
    }
        
}
