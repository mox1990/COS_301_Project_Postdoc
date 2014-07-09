/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.*;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Generator;
import com.softserve.system.Session;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlo
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserGateway implements UserGatewayLocal
 { // TODO: Finalize the local or remote spec
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public UserGateway(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }
    
    protected UserAccountManagementServices getUserAccountManagementServicesEJB()
    {
        return new UserAccountManagementServices(emf);
    }
    
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected Generator getGeneratorUTIL()
    {
        return new Generator();
    }
    
    
    @Override
    public Session login(HttpSession httpSession) throws AuthenticationException, Exception
    {        
        if(httpSession == null)
        {
            throw new Exception("Not httpsession given");
        }
        
        UserAccountManagementServices accounts = getUserAccountManagementServicesEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        //Looksup person from database
        Person user = accounts.getUserBySystemIDOrEmail((String) httpSession.getAttribute("systemID"));
        
        //If the user does not exist throw an exception
        if(user == null)
        {
            throw new AuthenticationException("The user does not exist");
        }
        
        //Check if httpsession systemID has the same as the entities systemID or email address
        if (((String) httpSession.getAttribute("systemID")).toLowerCase().equals(user.getSystemID().toLowerCase()) || ((String) httpSession.getAttribute("systemID")).toLowerCase().equals(user.getEmail().toLowerCase()))
        {
            //Checks if httpsession password and entities password still match
            if (httpSession.getAttribute("password").equals(user.getPassword())) 
            {
                //Set login status to true
                httpSession.setAttribute("status", Boolean.TRUE);
                //Log action
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("User logged in", user);
                auditTrailService.logAction(auditLog);
                //Return a session wrapper
                return new Session(httpSession, user);
            } 
            else
            {
                httpSession.setAttribute("status", Boolean.FALSE);
                throw new AuthenticationException("User password does not match");
            }
        }
        else
        {
            httpSession.setAttribute("status", Boolean.FALSE);
            throw new AuthenticationException("User username does not match");
        }
    }
    
    @Override
    public void logout(Session session) throws Exception
    {
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("User logged out", session.getUser());
        auditTrailService.logAction(auditLog);
        
        session.getSession().setAttribute("status", Boolean.FALSE);
    }
    
    @Override
    public Session getSessionFromHttpSession(HttpSession httpSession) throws AuthenticationException
    {
        UserAccountManagementServices accounts = getUserAccountManagementServicesEJB();
        Person user = accounts.getUserBySystemIDOrEmail((String) httpSession.getAttribute("systemID"));
        
        if(user != null)
        {
            return new Session(httpSession, user);
        }
        else
        {
            throw new AuthenticationException("The user does not exist");
        }
        
    }
    
    /**
     *
     * @param session
     * @param user
     * @param role
     * @return int which states the authentication value
     */
    @Override
    public void authenticateUser(Session session, List<SecurityRole> allowedRoles) throws AuthenticationException, Exception
    {
        UserAccountManagementServices accounts = getUserAccountManagementServicesEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        if(!session.getLoggedInStatus())
        {
            throw new AuthenticationException("The user is no longer logged in");
        }
        
        //Check if httpsession systemID still the same as the entities systemID or email address
        if (((String) session.getSession().getAttribute("systemID")).toLowerCase().equals(session.getUser().getSystemID().toLowerCase()) || ((String) session.getSession().getAttribute("systemID")).toLowerCase().equals(session.getUser().getEmail().toLowerCase()))
        {
            //Checks if httpsession password and entities password still match
            if (session.getSession().getAttribute("password").equals(session.getUser().getPassword())) 
            {
                //Check if user session has been given temporal system level access
                if(session.isSystem())
                {
                    return;
                }
                
                //Checks if user has the correct security role
                for(SecurityRole sr :session.getUser().getSecurityRoleList())
                {
                    if(allowedRoles.contains(sr))
                    {
                        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Authenticated user", session.getUser());
                        auditTrailService.logAction(auditLog);
                        return;
                    }
                }
                
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Unable to authenticate user", session.getUser());
                auditTrailService.logAction(auditLog);
                
                throw new AuthenticationException("User does not have the correct priviliges for this section");
                /*
                //What if the roles are in a different order???
                //Hard coded the size of the array...
                long[] roleID = new long[10];
                long[] roleIDSentIN = new long[10];
                List<SecurityRole> personRole = allPerson.getSecurityRoleList();
                for(int j = 0; j < personRole.size(); j++)
                {
                    roleID[j] = personRole.get(j).getRoleID();
                    roleIDSentIN[j] = role.get(j).getRoleID();                            
                }
                Arrays.sort(roleID);
                Arrays.sort(roleIDSentIN);
                for(int k = 0; k < roleID.length; k++)
                {
                    if(roleID[k] != roleIDSentIN[k])
                    {
                        //incorrect security role
                        check = 3;
                    }
                }
                */
            } 
            else
            {
                throw new AuthenticationException("User password does not match");
            }
        }
        else
        {
            throw new AuthenticationException("User username does not match");
        }
    }

    @Override
    public void authenticateUserAsOwner(Session session, Person person) throws AuthenticationException, Exception {
        if(!session.getUser().equals(person))
        {
            throw new AuthenticationException("User is not the owner");
        }
    }
    
    
    
    protected String generateRandomPassword()
    {
        return getGeneratorUTIL().generateRandomHexString();
    }
    
    
    @Override
    public void generateOnDemandAccount(Session session, String reason, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws Exception
    {
        //Use this to create a new account
        UserAccountManagementServices accountManagement = getUserAccountManagementServicesEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        //Set account to dorment
        user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DORMENT);
        //Set new random password
        user.setPassword(generateRandomPassword());
        
        //Create a user account using a system level system authentication
        accountManagement.createUserAccount(new Session(session.getSession(), session.getUser(), true), useManualSystemIDSpecification, user, userAddress, userUPInfo);
        
        //Notify the new user
        Notification notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), user, "Automatic account creation", "The user " + session.getUser().getCompleteName() + " has requested that a account be created for you for the following reasons: " + reason + ". Please visit inorder to activate your account. Log in with your email address and the following password " + user.getPassword());
        notificationService.sendNotification(notification, true);
        
        //Log this action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Generated on demand account", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    /**
     *
     * @param user
     * @param active
     */
    @Override
    public void activateOnDemandAccount(Session session, Person user) throws Exception
    {
        UserAccountManagementServices accountManagement = getUserAccountManagementServicesEJB();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
        accountManagement.updateUserAccount(session, user, null, null);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Activated on demand account", session.getUser());
        auditTrailService.logAction(auditLog);

    }
    
}
