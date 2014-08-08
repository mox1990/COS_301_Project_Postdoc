/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ProgressReportManagementServiceLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "progressReportApplicationSelectionRequestBean")
@RequestScoped
public class ProgressReportApplicationSelectionRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    
    /**
     * Creates a new instance of ProgressReportApplicationSelectionRequestBean
     */
    public ProgressReportApplicationSelectionRequestBean() {
    }
    
    public List<Application> getPendingApplications()
    {
        try 
        {
            return progressReportManagementServiceLocal.allApplicationsWithPendingReportsForUser(sessionManagerBean.getSession());
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ProgressReportApplicationSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<Application>();
        }
    }
    
    public void selectApplication(Application application)
    {
        sessionManagerBean.addObjectToSessionStorage("APPLICATION",application);
    }
    
    public String viewApplication(Application application)
    {
        selectApplication(application);
        return navigationManagerBean.goToRefereeReportServiceReportCreationView();
    }
}
