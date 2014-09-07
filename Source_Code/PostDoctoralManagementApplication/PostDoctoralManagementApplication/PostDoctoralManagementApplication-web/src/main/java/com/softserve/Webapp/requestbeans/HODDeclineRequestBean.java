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
import com.softserve.ejb.HODRecommendationServicesLocal;
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
@Named(value = "hodDeclineRequestBean")
@RequestScoped
public class HODDeclineRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private HODRecommendationServicesLocal hodRecommendationServicesLocal;
    
    private String reason = "";
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODDeclineRequestBean() {
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }
            
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String preformDeclineRequest()
    {
        try
        {
            hodRecommendationServicesLocal.declineAppliction(sessionManagerBean.getSession(), getSelectedApplication(), reason);
            return navigationManagerBean.goToHODApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(HODDeclineRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
