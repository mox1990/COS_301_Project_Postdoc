/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.fellow.ProgressReportContent;
import auto.softserve.XMLEntities.fellow.Reference;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Webapp.depenedentbeans.ProgressReportCreationDependBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.ProgressReportManagementServiceLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "progressReportCreationRequestBean")
@RequestScoped
public class ProgressReportCreationRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ProgressReportCreationDependBean progressReportCreationDependBean;
    
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    
    
    
    /**
     * Creates a new instance of ProgressReportCreationRequestBean
     */
    public ProgressReportCreationRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        progressReportCreationDependBean.init();
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }

    public ProgressReportCreationDependBean getProgressReportCreationDependBean() {
        return progressReportCreationDependBean;
    }

    public void setProgressReportCreationDependBean(ProgressReportCreationDependBean progressReportCreationDependBean) {
        this.progressReportCreationDependBean = progressReportCreationDependBean;
    }
    
    public String preformProgressReportCreationRequest()
    {
        try
        {   
            progressReportManagementServiceLocal.createProgressReport(sessionManagerBean.getSession(), getSelectedApplication(), progressReportCreationDependBean.getCombinedProgressReport());
            return navigationManagerBean.goToProgressReportManagementServiceApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ProgressReportCreationRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
}
