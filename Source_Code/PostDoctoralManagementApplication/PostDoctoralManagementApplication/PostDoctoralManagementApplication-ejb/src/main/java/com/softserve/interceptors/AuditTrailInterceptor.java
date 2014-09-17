/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.interceptors;

import com.softserve.DBEntities.AuditLog;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.ejb.AuditTrailServiceLocal;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.Parameter;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AuditTrailInterceptor {
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception
    {
        System.out.println("=====================Audit interceptor launching: " + context.getMethod().getName());
        Object result = null;
        String excptionMessage = "";
        Exception exception = null;
        try
        {
            result = context.proceed();
            System.out.println("=====================Audit interceptor method success: " + context.getMethod().getName());
        }
        catch(Exception ex)
        {
            System.out.println("=====================Audit interceptor caught method exception: " + context.getMethod().getName());
            excptionMessage = " [ Exception = " + ex.toString() + "]";
            exception = ex;            
        }
        
        System.out.println("Audit interceptor processing annotation: " + context.getMethod().getName());

        AuditableMethod[] auditableMethodAnnotations = context.getMethod().getDeclaredAnnotationsByType(AuditableMethod.class);        

        if(auditableMethodAnnotations.length > 0)
        {
            System.out.println("Auditing enabled: " + context.getMethod().getName());

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
                if(session.getUser() != null && session.getUser().getSystemID() != null)
                {
                    DBEntitiesFactory dBEntitiesFactory = new DBEntitiesFactory();
                    String logString = auditableMethodAnnotations[0].message();
                    if(auditableMethodAnnotations[0].logMethodName())
                    {
                        logString += " [ Method = " + context.getMethod().getName() + " ]";
                    }
                    if(auditableMethodAnnotations[0].logMethodParameters())
                    {
                        logString += " [ Parameters: ";
                        for(Object parameter : context.getParameters())
                        {
                            logString += parameter.toString() + "; ";
                        }

                        logString += "]";
                    }
                    logString += excptionMessage;
                    System.out.println("Auditied : " + context.getMethod().getName());
                    AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy(logString, session.getUser());
                    System.out.println("Auditmessage : " + auditLog.getAction());
                    auditTrailServiceLocal.logAction(new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE),auditLog);
                }
                else
                {
                    if(session.isSystem())
                    {
                        System.out.println("Audit interceptor skipping as session is system level: " + context.getMethod().getName());
                    }
                    else
                    {
                        System.out.println("Audit interceptor skipping as invalid session is found: " + context.getMethod().getName());
                    }
                }
                
            }
            else
            {
                throw new Exception("Cannot log a method that does not contain a session parameter");
            }            
        }
        else
        {
            System.out.println("Auditing disabled: " + context.getMethod().getName());
        }       
        
        if(exception != null)
        {
            throw exception;
        }
        else
        {
            return result;
        }
        
    }
}
