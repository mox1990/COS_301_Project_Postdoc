/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.interceptors;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.annotations.SecuredMethod;
import com.softserve.ejb.UserGatewayLocal;
import com.softserve.system.Session;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AuthenticationInterceptor {
    @EJB
    private UserGatewayLocal userGatewayLocal;
     
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception
    {

        System.out.println("=====================Authentication interceptor: " + context.getMethod().getName());
        SecuredMethod[] securedMethodAnnotations = context.getMethod().getDeclaredAnnotationsByType(SecuredMethod.class);
        Object result = null;
        if(securedMethodAnnotations.length > 0)
        {
            System.out.println("Authentication enabled: " + context.getMethod().getName());
            Session session = null;
            
            for(Object parameter : context.getParameters())
            {
                if(parameter.getClass() == Session.class)
                {
                    session = (Session) parameter;
                    break;
                }
            }
            
            if(session != null)
            {
                System.out.println("Authenticating: " + context.getMethod().getName());
                ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
                for(long roleID : securedMethodAnnotations[0].AllowedSecurityRoles())
                {
                    roles.add(new SecurityRole(roleID));
                }
                //Normal role authentication
                if(roles.size() >= 0 && !securedMethodAnnotations[0].ownerAuthentication())
                {
                    System.out.println("Normal authentication : " + context.getMethod().getName());
                    System.out.println("Allowed Security roles: " + roles.toString());
                    if(session.isSystem())
                    {
                        System.out.println("System level session");
                    }
                    else
                    {
                        System.out.println("User Security roles: " + session.getUser().getSecurityRoleList().toString());
                    }
                    userGatewayLocal.authenticateUser(session, roles);
                    
                }//Normal owner authentication
                else if(roles.isEmpty() && securedMethodAnnotations[0].ownerAuthentication())
                {                    
                    System.out.println("Owner authentication : " + context.getMethod().getName());
                    Object objToAuthenticate = context.getParameters()[securedMethodAnnotations[0].ownerParameterIndex()];
                    if(objToAuthenticate.getClass() == Person.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (Person) objToAuthenticate);
                    }
                    else if(objToAuthenticate.getClass() == Application.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (Application) objToAuthenticate);
                    }
                    else if(objToAuthenticate.getClass() == Cv.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (Cv) objToAuthenticate);
                    }
                    else if(objToAuthenticate.getClass() == ProgressReport.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (ProgressReport) objToAuthenticate);
                    }
                    else if(objToAuthenticate.getClass() == CommitteeMeeting.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (CommitteeMeeting) objToAuthenticate);
                    }
                    else if(objToAuthenticate.getClass() == FundingReport.class)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (FundingReport) objToAuthenticate);
                    }
                    else
                    {
                        throw new Exception("Invaild ownership object specified");
                    }
                }
                else
                {
                    try
                    {
                        System.out.println("Normal & Owner authentication : " + context.getMethod().getName());
                        userGatewayLocal.authenticateUser(session, roles);
                    }
                    catch(Exception ex)
                    {
                        Object objToAuthenticate = context.getParameters()[securedMethodAnnotations[0].ownerParameterIndex()];
                        if(objToAuthenticate.getClass() == Person.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (Person) objToAuthenticate);
                        }
                        else if(objToAuthenticate.getClass() == Application.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (Application) objToAuthenticate);
                        }
                        else if(objToAuthenticate.getClass() == Cv.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (Cv) objToAuthenticate);
                        }
                        else if(objToAuthenticate.getClass() == ProgressReport.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (ProgressReport) objToAuthenticate);
                        }
                        else if(objToAuthenticate.getClass() == CommitteeMeeting.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (CommitteeMeeting) objToAuthenticate);
                        }
                        else if(objToAuthenticate.getClass() == FundingReport.class)
                        {
                            userGatewayLocal.authenticateUserAsOwner(session, (FundingReport) objToAuthenticate);
                        }
                        else
                        {
                            throw new Exception("Invaild ownership object specified");
                        }
                    }
                }
                System.out.println("Authentication successful: " + context.getMethod().getName());
                result = context.proceed();
            }
            else
            {
                throw new Exception("Cannot authenticate a method that does not contain a session parameter");
            }            
        }
        else
        {
            System.out.println("Authentication disabled: " + context.getMethod().getName());
            result = context.proceed();
        }
        
        return result;
    }
}
