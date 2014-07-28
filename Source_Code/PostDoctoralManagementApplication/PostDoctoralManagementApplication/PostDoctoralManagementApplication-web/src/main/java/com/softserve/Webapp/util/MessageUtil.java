/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class MessageUtil {
    
    public static void CreateFacesMessageForComponent(UIComponent component, String title, String message, Severity severityLevel)
    {        
        FacesMessage facesMessage = new FacesMessage(severityLevel,title,message);
        FacesContext.getCurrentInstance().addMessage(component.getClientId(), facesMessage);
    }
    
    public static void CreateGlobalFacesMessage(String title, String message, Severity severityLevel)
    {        
        FacesMessage facesMessage = new FacesMessage(severityLevel,title,message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
