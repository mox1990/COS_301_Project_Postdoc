/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.util;

import com.softserve.Webapp.util.MessageUtil;
import com.softserve.webservices.auxiliary.Response;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ExceptionUtil {

    public ExceptionUtil() {
    }
            
    public static Response handleException(Class c, Exception ex)
    {        
        Logger.getLogger(c.getName()).log(Level.SEVERE, null, ex);
        
        return new Response(false, ex.toString(), new Object[0]);
    }
}
