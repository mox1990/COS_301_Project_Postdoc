/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.interceptors;

import com.softserve.auxiliary.annotations.PrePostConditionalMethod;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.ejb.nonapplicationservices.PrePostConditionalManagementServicesLocal;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PrePostConditionInterceptor {
    @EJB
    private PrePostConditionalManagementServicesLocal prePostConditionalManagementServicesLocal;  
    
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception
    {
        Object result;        
        PrePostConditionMethod prePostConditionMethod = null; 
        List<String> paramclasses = new ArrayList<String>();
        if(context.getMethod().getAnnotation(PrePostConditionalMethod.class) != null)
        {
            
            for(Class c : context.getMethod().getParameterTypes())
            {
                paramclasses.add(c.getName());
            }
            try
            {
                prePostConditionMethod = prePostConditionalManagementServicesLocal.findPrePostConditionMethodByClassAndName(new Session(null, null, Boolean.TRUE), context.getClass().getName(), context.getMethod().getName(), paramclasses);
            
                if(prePostConditionMethod.getPreConditionScript() != null && !prePostConditionalManagementServicesLocal.evaluatePreCondition(new Session(null, null, Boolean.TRUE), prePostConditionMethod, paramclasses, Arrays.asList(context.getParameters())))
                {
                    throw new Exception("The supplied information did not meet the preconditions");
                }
            }
            catch(Exception ex)
            {
                
                Logger.getLogger(PrePostConditionInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                
                throw ex;
            }
        }
        
        result = context.proceed();
        
        if(context.getMethod().getAnnotation(PrePostConditionalMethod.class) != null)
        {
            if(prePostConditionMethod == null || (prePostConditionMethod.getPostConditionScript()!= null && !prePostConditionalManagementServicesLocal.evaluatePostCondition(new Session(null, null, Boolean.TRUE), prePostConditionMethod, paramclasses, Arrays.asList(context.getParameters()))))
            {
                throw new Exception("The supplied information did not meet the postconditions");
            }
        }
        
        return result;
    }
}
