/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlo
 */
@Interceptors({AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserGateway implements UserGatewayLocal
 { // TODO: Finalize the local or remote spec
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;    

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }    
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    public UserGateway() {
    }
    
    public UserGateway(EntityManagerFactory emf) {
        this.emf = emf;
    }
        
    @AuditableMethod
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
            
        //Check if httpsession systemID has the same as the entities systemID or email address
        if (session.doesHttpSessionUsernameMatchUserUsername() || session.doesHttpSessionUsernameMatchUserEmail())
        {            
            //Checks if httpsession password and entities password still match
            if (session.doesHttpSessionPasswordMatchUserPassword()) 
            {
                
                //Set login status to true
                session.setLoggedInStatus(Boolean.TRUE);
            } 
            else
            {
                session.setLoggedInStatus(Boolean.FALSE);
                throw new AuthenticationException("User's username or password does not match");
            }
        }
        else
        {
            session.setLoggedInStatus(Boolean.FALSE);
            throw new AuthenticationException("User's username or password does not match");
        }
    }
    
    @AuditableMethod
    @Override
    public void logout(Session session) throws Exception
    {        
        session.setLoggedInStatus(Boolean.FALSE);
        session.setHttpSessionUsername("");
        session.setHttpSessionPassword("");        
    }
    
    
    @Override
    public Session getSessionFromHttpSession(HttpSession httpSession) throws AuthenticationException, NoSuchAlgorithmException
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            Person user = getDAOFactory(em).createPersonDAO().findUserBySystemIDOrEmail((String) httpSession.getAttribute("username"));
            
            if(user != null)
            {
                return new Session(httpSession, user);
            }
            else
            {
                throw new AuthenticationException("The user does not exist");
            }
        }
        finally
        {
            em.close();
        }        
    }
    
    /**
     *
     * @param session
     * @param user
     * @param role
     * @return int which states the authentication value
     */
    //@AuditableMethod(message = "User authentication")
    @Override
    public void authenticateUser(Session session, List<SecurityRole> allowedRoles) throws AuthenticationException, Exception
    {
        
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
                        return;
                    }
                }
                
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
    
    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, Person person) throws AuthenticationException, Exception {
        if(session.getUser() == null || !session.getUser().equals(person))
        {
            throw new AuthenticationException("You are not the owner of this item");
        }
    } 
    
    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, Application application) throws Exception 
    {
        authenticateUserAsOwner(session, application.getFellow());
    }

    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, Cv cv) throws Exception 
    {
        authenticateUserAsOwner(session, cv.getPerson());
    }
    
    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, ProgressReport progressReport) throws Exception 
    {
        authenticateUserAsOwner(session, progressReport.getApplication().getFellow());
    }
    
    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, CommitteeMeeting committeeMeeting) throws Exception 
    {
        authenticateUserAsOwner(session, committeeMeeting.getOrganiser());
    }
    
    @AuditableMethod
    @Override
    public void authenticateUserAsOwner(Session session, FundingReport fundingReport) throws Exception 
    {
        authenticateUserAsOwner(session, fundingReport.getDris());
    }
    
}
