/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softserve.webservices.auxiliary.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PayloadUtil {

    public PayloadUtil() {
    }
    
    public static String createSuccesPayLoad(Object[] payLoad)
    {
        try 
        {
            return JSONConverterUtil.objectToJSON_JACKSON(new Response(true, "", payLoad));
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PayloadUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "{\"success\":false,\"exceptionMessage\":\"" + ex.toString() + "\",\"result\":null}";
        }
    }
    
    public static String createFailurePayLoad(Class class1, Exception exception)
    {
        try 
        {
            return JSONConverterUtil.objectToJSON_JACKSON(ExceptionUtil.handleException(class1, exception));
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PayloadUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "{\"success\":false,\"exceptionMessage\":\"" + ex.toString() + "\",\"result\":null}";
        }
    }
}
