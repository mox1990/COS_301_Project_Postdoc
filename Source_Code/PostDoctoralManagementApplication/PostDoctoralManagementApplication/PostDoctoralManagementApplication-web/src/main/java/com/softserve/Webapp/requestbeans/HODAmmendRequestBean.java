/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.RecommendationReport;
import com.softserve.Webapp.HODServicesBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hodAmmendRequestBean")
@RequestScoped
public class HODAmmendRequestBean {

    @Inject
    private HODServicesBean hodServicesBean;
    
    private String reason = "";
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODAmmendRequestBean() {
    }
    
    public UIComponent getErrorContainer() 
    {
        return hodServicesBean.getErrorContainer();
    }

    public void setErrorContainer(UIComponent errorContainer) 
    {
        hodServicesBean.setErrorContainer(errorContainer);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String preformAmmendRequest()
    {
        return hodServicesBean.ammendCurrentlySelectedApplication(reason);
    }
    
}
