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
        Object result = context.proceed();
        
        AuditableMethod[] auditableMethodAnnotations = context.getMethod().getDeclaredAnnotationsByType(AuditableMethod.class);        
        
        if(auditableMethodAnnotations.length > 0)
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
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy(null, session.getUser());
                auditTrailServiceLocal.logAction(auditLog);
            }
            else
            {
                throw new Exception("Cannot log a method that does not contain a session parameter");
            }            
        }
        
        return result;
    }
}
