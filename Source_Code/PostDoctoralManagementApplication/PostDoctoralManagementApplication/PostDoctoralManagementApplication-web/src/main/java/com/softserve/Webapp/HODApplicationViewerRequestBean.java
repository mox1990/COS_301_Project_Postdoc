/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import auto.softserve.XMLEntities.CV.*;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import javax.enterprise.context.RequestScoped;
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
    private HODServicesBean hodServicesBean;
    
    /**
     * Creates a new instance of HODApplicationViewerRequestBean
     */
    public HODApplicationViewerRequestBean() {
    }
    
    public Application getApplication()
    {
        return hodServicesBean.getCurrentlySelectedApplication();
    } 
    
}
