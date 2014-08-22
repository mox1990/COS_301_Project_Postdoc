/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.EJB;
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
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected AuditTrailServiceLocal getAuditTrailServiceEJB()
    {
        return auditTrailServiceLocal;
    }
    
     protected UserAccountManagementServiceLocal getUserAccountManagementServicesEJB()
    {
        return new UserAccountManagementService(emf);
    }

    public UserGateway() {
    }
    
    public UserGateway(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
   
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    
    @Override
    public void login(Session session) throws AuthenticationException, Exception
    {        
        
        if(session == null)
        {
            throw new Exception("Session is null");
        }
        
        if(session.isUserAccountDisabled())
        {
            throw new AuthenticationException("User account disabled");
        }
        
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();        
        //Check if httpsession systemID has the same as the entities systemID or email address
        if (session.doesHttpSessionUsernameMatchUserUsername() || session.doesHttpSessionUsernameMatchUserEmail())
        {            
            //Checks if httpsession password and entities password still match
            if (session.doesHttpSessionPasswordMatchUserPassword()) 
            {
                //Set login status to true
                session.setLoggedInStatus(Boolean.TRUE);
                //Log action
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("User logged in", session.getUser());
                auditTrailService.logAction(auditLog);
            } 
            else
            {
                session.setLoggedInStatus(Boolean.FALSE);
                throw new AuthenticationException("User password does not match");
            }
        }
        else
        {
            session.setLoggedInStatus(Boolean.FALSE);
            throw new AuthenticationException("User username does not match");
        }
    }
    
    @Override
    public void logout(Session session) throws Exception
    {
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        session.setLoggedInStatus(Boolean.FALSE);
        session.setHttpSessionUsername("");
        session.setHttpSessionPassword("");
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("User logged out", session.getUser());
        auditTrailService.logAction(auditLog);
        
        
    }
    
    @Override
    public Session getSessionFromHttpSession(HttpSession httpSession) throws AuthenticationException
    {
        UserAccountManagementServiceLocal accounts = getUserAccountManagementServicesEJB();
        Person user = accounts.getUserBySystemIDOrEmail((String) httpSession.getAttribute("username"));
        
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
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        //Check if user session has been given temporal system level access
        if(session.isSystem())
        {
            return;
        }
        
        if(!session.getLoggedInStatus())
        {
            throw new AuthenticationException("The user is no longer logged in");
        }
        
        if(!session.isUserAccountActive())
        {
            throw new AuthenticationException("The user is not a active user");
        }
        
        //Check if httpsession systemID still the same as the entities systemID or email address
        if (session.doesHttpSessionUsernameMatchUserUsername() || session.doesHttpSessionUsernameMatchUserEmail())
        {
            //Checks if httpsession password and entities password still match
            if (session.doesHttpSessionPasswordMatchUserPassword()) 
            {                
                
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
        if(session.getUser() == null || !session.getUser().equals(person))
        {
            throw new AuthenticationException("You are not the owner of this item");
        }
    } 
    
}
