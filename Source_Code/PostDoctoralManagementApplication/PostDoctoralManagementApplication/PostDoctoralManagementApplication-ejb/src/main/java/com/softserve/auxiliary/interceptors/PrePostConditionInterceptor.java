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
import java.lang.reflect.Parameter;
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
        System.out.println("=====================PrePostConditionMethod interceptor: " + context.getMethod().getName());
        Object result;        
        PrePostConditionMethod prePostConditionMethod = null; 
        List<String> paramclasses = new ArrayList<String>();
        List<String> paramnames = new ArrayList<String>();
        PrePostConditionalMethod annotation = context.getMethod().getAnnotation(PrePostConditionalMethod.class);
        if(annotation != null)
        {
            System.out.println("=====================PrePostConditionMethod interceptor: method has annotation");
            for(Class c : context.getMethod().getParameterTypes())
            {
                paramclasses.add(c.getName());
                
            }
            
            for(Parameter c : context.getMethod().getParameters())
            {
                paramnames.add(c.getName());
                
            }
            
            prePostConditionMethod = prePostConditionalManagementServicesLocal.findPrePostConditionMethodByClassAndName(new Session(null, null, Boolean.TRUE), context.getMethod().getDeclaringClass().getName(), context.getMethod().getName(), paramclasses);
            
            if(annotation.preActive())
            {
                System.out.println("=====================PrePostConditionMethod interceptor: method pre condition active");
                try
                {
                    System.out.println("=====================PrePostConditionMethod interceptor: method pre condition evaluation... " + prePostConditionMethod);
                    if(prePostConditionMethod != null && prePostConditionMethod.getPreConditionScript() != null && !prePostConditionMethod.getPreConditionScript().equals("") && !prePostConditionalManagementServicesLocal.evaluatePreCondition(new Session(null, null, Boolean.TRUE), prePostConditionMethod, paramnames, Arrays.asList(context.getParameters())))
                    {
                        throw new Exception("The supplied information did not meet the preconditions");
                    }
                    else
                    {
                        if(prePostConditionMethod != null)
                        {
                            System.out.println(prePostConditionMethod.getPreConditionScript());
                        }
                    }
                    System.out.println("=====================PrePostConditionMethod interceptor: method pre condition evaluation successful...");
                }
                catch(Exception ex)
                {

                    Logger.getLogger(PrePostConditionInterceptor.class.getName()).log(Level.SEVERE, null, ex);

                    throw ex;
                }
            }
        }
        System.out.println("=====================PrePostConditionMethod interceptor: running method...");
        result = context.proceed();
        
        if(annotation != null && annotation.postActive())
        {
            System.out.println("=====================PrePostConditionMethod interceptor: method post condition active...");
            try
            {
                System.out.println("=====================PrePostConditionMethod interceptor: method post condition evaluation...");
                if(prePostConditionMethod != null && prePostConditionMethod.getPostConditionScript()!= null  && !prePostConditionMethod.getPostConditionScript().equals("") && !prePostConditionalManagementServicesLocal.evaluatePostCondition(new Session(null, null, Boolean.TRUE), prePostConditionMethod, paramnames, Arrays.asList(context.getParameters())))
                {
                    throw new Exception("The supplied information did not meet the postconditions");
                }
                System.out.println("=====================PrePostConditionMethod interceptor: method post condition evaluation successful...");
            }
            catch(Exception ex)
            {
                Logger.getLogger(PrePostConditionInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            }
        }
        System.out.println("=====================PrePostConditionMethod interceptor: complete");
        return result;
    }
}
