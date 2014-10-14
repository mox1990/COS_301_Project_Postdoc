/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.util;

import com.softserve.auxiliary.annotations.PrePostConditionalMethod;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ClassMethodVerificationUtil {
    public boolean doesMethodExist(String className, String methodName, List<String> parameters)
    {
        return findMethod(className, methodName, parameters) != null;
    }
    
    public Method findMethod(String className, String methodName, List<String> parameters)
    {
        try
        {                
            Class class1 = Class.forName(className);            
            
            for(Method method: class1.getMethods())
            {
                if(method.getName().equals(methodName) && method.getAnnotation(PrePostConditionalMethod.class) != null)
                {
                    if(parameters.size() == method.getParameterTypes().length)
                    {                    
                        boolean isMatch = true;
                        for(int i = 0; i < method.getParameterTypes().length; i++)
                        {

                            Class c1 = Class.forName(parameters.get(i));
                            if(!c1.equals(method.getParameterTypes()[i]))
                            {
                                isMatch = false;
                                break;
                            }
                        }
                        
                        if(isMatch)
                        {
                            return method;
                        }
                    }
                }
                
            }
            
            return null;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}
