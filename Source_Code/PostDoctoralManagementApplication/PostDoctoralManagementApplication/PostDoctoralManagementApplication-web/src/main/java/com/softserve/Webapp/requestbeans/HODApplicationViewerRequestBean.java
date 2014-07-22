/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.CV.*;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.ejb.HODRecommendationServices;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hodApplicationViewerRequestBean")
@RequestScoped
public class HODApplicationViewerRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private HODRecommendationServices hodRecommendationServices;
    
    private UIComponent errorContainer;
    
    /**
     * Creates a new instance of HODApplicationViewerRequestBean
     */
    public HODApplicationViewerRequestBean() {
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStroage(0, Application.class);
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    } 
    
}
