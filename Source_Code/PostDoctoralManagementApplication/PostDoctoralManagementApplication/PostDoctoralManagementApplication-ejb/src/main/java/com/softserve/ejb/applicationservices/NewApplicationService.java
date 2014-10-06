/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.*;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.constants.PersistenceConstants.*;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.ArrayList;
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
public class NewApplicationService implements  NewApplicationServiceLocal{

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    public NewApplicationService() {
    }

    public NewApplicationService(EntityManagerFactory emf) {
        this.emf = emf;
    }
            
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
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Updated/Created CV")
    @Override
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Created/Updated a new application")
    @Override
    public void createNewApplication(Session session, Application application) throws AuthenticationException, Exception
    {  
        if(application == null)
        {
            throw new Exception("Application is not valid");
        }
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
        
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Linked grant holder to new application")
    @Override
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception
    {        
        if(grantHolder == null)
        {
            throw new Exception("Grant holder is not valid");
        }
        
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationJpaController applicationJpaController = getDAOFactory(em).createApplicationDAO();
            UserAccountManagementServiceLocal accountManagementServices = getUserAccountManagementServiceEJB();

            //Check if grant holder already exists
            if(!(grantHolder.getSystemID() != null && accountManagementServices.getUserBySystemID(grantHolder.getSystemID()) != null && accountManagementServices.getUserBySystemID(grantHolder.getSystemID()).equals(grantHolder)))
            {
                grantHolder.setAddressLine1(new Address());

                List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
                securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
                grantHolder.setSecurityRoleList(securityRoles);

                accountManagementServices.generateOnDemandAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), session.getUser().getCompleteName() + " has requested you be a grant holder for their post doctoral application", true, grantHolder);
            }
            else if(grantHolder.getSystemID() != null)
            {
                if(!grantHolder.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER))
                {
                    grantHolder.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
                    accountManagementServices.updateUserAccount(new Session(session.getHttpSession(),session.getUser(), true), grantHolder);
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
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            
            Application a = applicationJpaController.findApplication(application.getApplicationID());
        
            if(a.getGrantHolder() == null || !a.getGrantHolder().equals(grantHolder))
            {
                //Link grant holder to application
                a.setGrantHolder(grantHolder);
                applicationJpaController.edit(a);
            } 

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Linked referee to new application")
    @Override
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception
    {
        
        if(referee == null)
        {
            throw new Exception("Referee is not valid");
        }  
        
        EntityManager em = emf.createEntityManager();

        try
        {
            ApplicationJpaController applicationJpaController = getDAOFactory(em).createApplicationDAO();
            UserAccountManagementServiceLocal accountManagementServices = getUserAccountManagementServiceEJB();

            //Check if referee already exists
            if(!(referee.getSystemID() != null && accountManagementServices.getUserBySystemID(referee.getSystemID()) != null && accountManagementServices.getUserBySystemID(referee.getSystemID()).equals(referee)))
            {
                referee.setAddressLine1(new Address());

                List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
                securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
                referee.setSecurityRoleList(securityRoles);

                accountManagementServices.generateOnDemandAccount(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE), session.getUser().getCompleteName() + " has requested you be a referee for their post doctoral application", false, referee);
            }
            else if(referee.getSystemID() != null)
            {
                if(!referee.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE))
                {
                    referee.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
                    accountManagementServices.updateUserAccount(new Session(session.getHttpSession(),session.getUser(), true), referee);
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
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod(message = "Submitted a new application")
    @Override
    public void submitApplication(Session session, Application application) throws Exception
    {        
          
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {            
            getApplicationServicesUTIL(transactionController.getEntityManager()).submitApplication(application); 

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
    
    @Override
    public boolean canFellowOpenANewApplication(Person fellow)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            List<Application> applications = getDAOFactory(em).createApplicationDAO().findAllApplicationsWhosFellowIs(fellow);
            for(Application application: applications)
            {
                if(!(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED) || application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED) || application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED)))
                {
                    return false;
                }
            }

            return true;
        }
        finally
        {
            em.close();
        }
        
        
    }   
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW})
    @AuditableMethod
    @Override
    public Application getOpenApplication(Session session) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            List<Application> applications = getDAOFactory(em).createApplicationDAO().findAllApplicationsWhosFellowIs(session.getUser());
            for(Application application: applications)
            {
                if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
                {
                    return application;
                }
            }

            return null;
        }
        finally
        {
            em.close();
        }
        
    }
    
}
