/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hODDeclineRequestBean")
@RequestScoped
public class HODDeclineRequestBean {
    
    @Inject
    private HODServicesBean hODServicesBean;
    
    private String reason = "";
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODDeclineRequestBean() {
    }
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public void preformDeclineRequest()
    {
        hODServicesBean.declineCurrentlySelectedApplication(reason);
    }
    
}
