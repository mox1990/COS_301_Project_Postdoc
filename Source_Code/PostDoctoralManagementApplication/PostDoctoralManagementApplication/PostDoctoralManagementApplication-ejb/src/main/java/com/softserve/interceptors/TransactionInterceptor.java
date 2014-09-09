/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.interceptors;
import com.softserve.annotations.TransactionMethod;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class TransactionInterceptor {
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception
    {
        Object result;
        
        if(context.getMethod().getAnnotation(TransactionMethod.class) != null)
        {
        
            UserTransaction userTransaction = com.softserve.constants.PersistenceConstants.getUserTransaction();

            try
            {
                userTransaction.begin();
            }
            catch(Exception ex)
            {
                throw new Exception("Transaction start failed due to " + ex.toString() + ". Database remains unchanged.", ex);
            }

            try
            {
                result = context.proceed();
            }
            catch(Exception ex)
            {            
                userTransaction.rollback();
                throw new Exception("Transaction rolled back due to " + ex.toString() + ". Database remains unchanged.", ex);           
            }

            try
            {
                userTransaction.commit();
            }
            catch(Exception ex)
            {
                throw new Exception("Transaction commit failed due to " + ex.toString() + ". Database remains unchanged.", ex);
            }  
        }
        else
        {
            result = context.proceed();
        }
        
        return result;        
    }
}
