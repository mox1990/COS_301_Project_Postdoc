/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
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
public class NewApplicationService implements  NewApplicationServiceLocal{

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public NewApplicationService() {
    }

    public NewApplicationService(EntityManagerFactory emf) {
        this.emf = emf;
    }
            
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
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    @Override
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
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
        
        if(application == null)
        {
            throw new Exception("Application is not valid");
        }
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Set status and application type
        if(application.getApplicationID() == null || applicationJpaController.findApplication(application.getApplicationID()) == null)
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_NEW);
            application.setTimestamp(getGregorianCalendar().getTime());
            applicationJpaController.create(application);
        }
        else
        {
            applicationJpaController.edit(application);
        }
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Opened a new application", session.getUser());
        auditTrailService.logAction(auditLog);
                
    }
    
    @Override
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception
    {
        
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        if(grantHolder == null)
        {
            throw new Exception("Grant holder is not valid");
        }
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        UserAccountManagementService accountManagementServices = getUserAccountManagementServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Check if grant holder already exists
        if(!(grantHolder.getSystemID() != null && accountManagementServices.getUserBySystemID(grantHolder.getSystemID()) != null && accountManagementServices.getUserBySystemID(grantHolder.getSystemID()).equals(grantHolder)))
        {
            grantHolder.setAddressLine1(new Address());
                            
            List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
            grantHolder.setSecurityRoleList(securityRoles);
            
            accountManagementServices.generateOnDemandAccount(session, session.getUser().getCompleteName() + " has requested you be a grant holder for their post doctoral application", true, grantHolder);
        }
        else if(grantHolder.getSystemID() != null)
        {
            if(!grantHolder.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER))
            {
                grantHolder.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
                accountManagementServices.updateUserAccount(new Session(session.getHttpSession(),session.getUser(), true), grantHolder);
            }            
        }
        
        Application a = applicationJpaController.findApplication(application.getApplicationID());
        
        if(a.getGrantHolder() == null || !a.getGrantHolder().equals(grantHolder))
        {
            //Link grant holder to application
            a.setGrantHolder(grantHolder);
            System.out.println("===========Linking " + a.getGrantHolder().toString());
            System.out.println("===========Linking to" + a.toString());
            applicationJpaController.edit(a);
        }
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Linked grant holder to new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        if(referee == null)
        {
            throw new Exception("Referee is not valid");
        }        
        
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        UserAccountManagementService accountManagementServices = getUserAccountManagementServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        //Check if referee already exists
        if(!(referee.getSystemID() != null && accountManagementServices.getUserBySystemID(referee.getSystemID()) != null && accountManagementServices.getUserBySystemID(referee.getSystemID()).equals(referee)))
        {
            referee.setAddressLine1(new Address());
              
            List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
            referee.setSecurityRoleList(securityRoles);
            
            accountManagementServices.generateOnDemandAccount(session, session.getUser().getCompleteName() + " has requested you be a referee for their post doctoral application", false, referee);
        }
        else if(referee.getSystemID() != null)
        {
            if(!referee.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE))
            {
                referee.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
                accountManagementServices.updateUserAccount(new Session(session.getHttpSession(),session.getUser(), true), referee);
            }            
        }
        
        Application a = applicationJpaController.findApplication(application.getApplicationID());
        
        if(a.getPersonList() == null)
        {
            a.setPersonList(new ArrayList<Person>());
        }
        
        System.out.println(referee.toString());
        System.out.println("=======Contains: " + application.getPersonList().contains(referee));
        
        if(!a.getPersonList().contains(referee))
        {
            System.out.println("=======Linking referee " + referee.toString());
            System.out.println("=======Linking referee to " + a.toString());
            //Link referee to application
            a.getPersonList().add(referee);
            applicationJpaController.edit(a);
        }
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Linked referee to new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public void submitApplication(Session session, Application application) throws Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        if(application == null)
        {
            throw new Exception("Application is not valid");
        }
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        NotificationService notificationService = getNotificationServiceEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        application = applicationJpaController.findApplication(application.getApplicationID());
        
        //Set application status
        application.setSubmissionDate(getGregorianCalendar().getTime());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
        applicationJpaController.edit(application);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Submitted new application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public boolean canFellowOpenANewApplication(Person fellow)
    {
        for(Application application: fellow.getApplicationList())
        {
            if(!(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED) || application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED)))
            {
                return false;
            }
        }
        
        return true;
    }   
    
    
    @Override
    public Application getOpenApplication(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        
        for(Application application: session.getUser().getApplicationList1())
        {
            if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
            {
                return application;
            }
        }
        
        return null;
    }
    
}
