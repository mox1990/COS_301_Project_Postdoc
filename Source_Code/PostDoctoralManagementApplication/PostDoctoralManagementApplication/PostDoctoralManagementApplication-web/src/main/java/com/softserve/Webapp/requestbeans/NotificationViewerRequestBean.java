/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.ejb.NotificationServiceLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "notificationViewerRequestBean")
@RequestScoped
public class NotificationViewerRequestBean {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    
    /**
     * Creates a new instance of NotificationViewerRequestBean
     */
    public NotificationViewerRequestBean() {
    }
    
    
    
}
