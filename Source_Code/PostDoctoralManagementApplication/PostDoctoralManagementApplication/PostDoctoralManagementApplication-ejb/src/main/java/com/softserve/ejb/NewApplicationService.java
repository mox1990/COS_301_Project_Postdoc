/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.*;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Carlo
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NewApplicationService implements  NewApplicationServiceLocal{

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    protected UserAccountManagementService getUserAccountManagementServiceEJB()
    {
        return new UserAccountManagementService(emf);
    }
    
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }
    
    protected CVManagementService getCVManagementServiceEJB()
    {
        return new CVManagementService(emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    @Override
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        getCVManagementServiceEJB().createCV(session, cv);
    }
    
    @Override
    public void createNewApplication(Session session, Application application) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Set status and application type
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
        application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_NEW);
        applicationJpaController.create(application);
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Opened a new application", session.getUser());
        auditTrailService.logAction(auditLog);
                
    }
    
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        UserAccountManagementService accountManagementServices = getUserAccountManagementServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Check if grant holder already exists
        if(!(grantHolder.getSystemID() != null && accountManagementServices.getUserBySystemID(grantHolder.getSystemID()).equals(grantHolder)))
        {
            accountManagementServices.generateOnDemandAccount(session, session.getUser().getCompleteName() + " has requested you be a grant holder for their post doctoral application", false, grantHolder,new Address(), null);
        }
        
        //Link grant holder to application
        application.setGrantHolderID(grantHolder);
        applicationJpaController.edit(application);
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Linked grant holder to new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        UserAccountManagementService accountManagementServices = getUserAccountManagementServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Check if referee already exists
        if(!(referee.getSystemID() != null && accountManagementServices.getUserBySystemID(referee.getSystemID()).equals(referee)))
        {
            accountManagementServices.generateOnDemandAccount(session, session.getUser().getCompleteName() + " has requested you be a referee for their post doctoral application", false, referee,new Address(), null);
        }
        
        //Link referee to application
        application.getRefereeList().add(referee);
        applicationJpaController.edit(application);
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Linked referee to new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    public void submitApplication(Session session, Application application) throws Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        NotificationService notificationService = getNotificationServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        //Set application status
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
        applicationJpaController.create(application);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Submitted new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    

    
    
}
