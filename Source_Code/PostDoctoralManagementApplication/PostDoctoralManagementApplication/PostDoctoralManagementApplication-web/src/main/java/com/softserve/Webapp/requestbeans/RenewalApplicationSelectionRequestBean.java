/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.conversationbeans.ApplicationRenewalBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationRenewalServiceLocal;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "renewalApplicationSelectionRequestBean")
@RequestScoped
public class RenewalApplicationSelectionRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    
    /**
     * Creates a new instance of RenewalApplicationSelectionRequestBean
     */
    public RenewalApplicationSelectionRequestBean() {
    }
    
    public List<Application> getRenewableApplications()
    {
        try
        {
            Session session = sessionManagerBean.getSession();
            return applicationRenewalServiceLocal.getRenewableApplicationsForFellow(session, session.getUser());
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<Application>();
        }
    }
    
    public void selectApplication(Application application)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("APPLICATION", application);
    }
    
    public String renewApplicationWizard(Application application)
    {
        selectApplication(application);
        return navigationManagerBean.goToApplicationRenewalServiceWizardView();
    }
    
}
