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
import com.softserve.ejb.RefereeReportServiceLocal;
import java.util.ArrayList;
import java.util.List;
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
@Named(value = "refereeApplicationSelectionRequestBean")
@RequestScoped
public class refereeApplicationSelectionRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private RefereeReportServiceLocal refereeReportServiceLocal;
    
    private UIComponent errorContainer;
    
    /**
     * Creates a new instance of refereeApplicationSelectionRequestBean
     */
    public refereeApplicationSelectionRequestBean() {
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }
            
    public List<Application> getPendingApplications()
    {   
        try
        {
            return refereeReportServiceLocal.loadPendingApplications(sessionManagerBean.getSession(), 0, refereeReportServiceLocal.countTotalPendingApplications(sessionManagerBean.getSession()));
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
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
