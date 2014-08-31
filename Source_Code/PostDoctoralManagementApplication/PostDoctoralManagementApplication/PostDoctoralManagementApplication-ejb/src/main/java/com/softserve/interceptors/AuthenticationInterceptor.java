/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.interceptors;

import com.softserve.DBEntities.Person;
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

        SecuredMethod[] securedMethodAnnotations = context.getMethod().getDeclaredAnnotationsByType(SecuredMethod.class);
        Object result = null;
        if(securedMethodAnnotations.length > 0)
        {
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
                ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
                for(long roleID : securedMethodAnnotations[0].AllowedSecurityRoles())
                {
                    roles.add(new SecurityRole(roleID));
                }
                //Normal role authentication
                if(roles.size() >= 0 && !securedMethodAnnotations[0].ownerAuthentication())
                {
                    userGatewayLocal.authenticateUser(session, roles);
                }//Normal owner authentication
                else if(roles.isEmpty() && securedMethodAnnotations[0].ownerAuthentication())
                {                    
                    userGatewayLocal.authenticateUserAsOwner(session, (Person) context.getParameters()[securedMethodAnnotations[0].ownerParameterIndex()]);
                }
                else
                {
                    try
                    {
                        userGatewayLocal.authenticateUser(session, roles);
                    }
                    catch(Exception ex)
                    {
                        userGatewayLocal.authenticateUserAsOwner(session, (Person) context.getParameters()[securedMethodAnnotations[0].ownerParameterIndex()]);
                    }
                }
                
                context.proceed();
            }
            else
            {
                throw new Exception("Cannot authenticate a method that does not contain a session parameter");
            }            
        }
        else
        {
            result = context.proceed();
        }
        
        return result;
    }
}
