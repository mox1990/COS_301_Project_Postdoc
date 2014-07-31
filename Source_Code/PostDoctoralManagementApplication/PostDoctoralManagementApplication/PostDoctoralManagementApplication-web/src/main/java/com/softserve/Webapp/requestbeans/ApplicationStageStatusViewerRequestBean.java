/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationProgressViewerServiceLocal;
import com.softserve.system.ApplicationStageStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationStageStatusViewerRequestBean")
@Dependent
public class ApplicationStageStatusViewerRequestBean {

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @EJB
    private ApplicationProgressViewerServiceLocal applicationProgressViewerServiceLocal;
    
    /**
     * Creates a new instance of ApplicationStageStatusViewerRequestBean
     */
    public ApplicationStageStatusViewerRequestBean() {
    }
    
    public List<ApplicationStageStatus> getApplicationStageStatuses()
    {
        try 
        {
            return applicationProgressViewerServiceLocal.getApplicationProgress(sessionManagerBean.getSession(), getSelectedApplication());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationStageStatusViewerRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            
            return new ArrayList<ApplicationStageStatus>();
        }
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage(0, Application.class);
    }
}
