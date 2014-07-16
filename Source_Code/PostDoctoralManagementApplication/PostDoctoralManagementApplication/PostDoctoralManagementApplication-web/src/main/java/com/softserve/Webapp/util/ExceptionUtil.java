/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ExceptionUtil {

    public ExceptionUtil() {
    }
    
    public static void handleException(UIComponent errorDisplayer, Exception ex)
    {        
        FacesMessage facesMessage = new FacesMessage(ex.getMessage());
        if(errorDisplayer == null)
        {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(errorDisplayer.getClientId(), facesMessage);
        }
    }
}