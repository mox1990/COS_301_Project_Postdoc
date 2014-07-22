/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.HODRecommendationServices;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hodDeclineRequestBean")
@RequestScoped
public class HODDeclineRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private HODRecommendationServices hodRecommendationServices;
    
    private UIComponent errorContainer;
    
    private String reason = "";
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODDeclineRequestBean() {
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
            
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String preformAmmendRequest()
    {
        try
        {
            hodRecommendationServices.denyAppliction(sessionManagerBean.getSession(), getSelectedApplication(), reason);
            return navigationManagerBean.goToPreviousBreadCrumb();
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
}
