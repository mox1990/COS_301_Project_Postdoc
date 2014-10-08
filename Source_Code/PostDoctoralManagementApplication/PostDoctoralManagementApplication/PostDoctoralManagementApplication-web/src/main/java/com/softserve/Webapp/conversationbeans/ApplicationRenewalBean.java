/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.Webapp.depenedentbeans.ApplicationCreationDependBean;
import com.softserve.Webapp.depenedentbeans.CVCreationDependBean;
import com.softserve.Webapp.depenedentbeans.ProgressReportCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationRenewalBean")
@ConversationScoped
public class ApplicationRenewalBean implements Serializable {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject 
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation convesation;
    
    @Inject
    private ProgressReportCreationDependBean progressReportCreationDependBean;
    @Inject
    private CVCreationDependBean cVCreationDependBean;
    @Inject
    private ApplicationCreationDependBean applicationCreationDependBean;
    
    @EJB
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    
    private Application oldApplication;
    
    private int wizardActiveTab;
    private final int MAX_TAB_INDEX = 3;
    
    /**
     * Creates a new instance of ApplicationRenewalBean
     */
    public ApplicationRenewalBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(convesation);
        conversationManagerBean.startConversation(convesation);
        try
        {   
            oldApplication = sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class); 
            
            if(oldApplication == null)
            {
                throw new Exception("No application selected");
            }

            applicationCreationDependBean.init(new Application());
            cVCreationDependBean.init(oldApplication.getFellow().getCv());
            progressReportCreationDependBean.init(null);       
            
            if(applicationRenewalServiceLocal.doesApplicationHaveFinalProgressReport(sessionManagerBean.getSession(),oldApplication))
            {
                wizardActiveTab = 1;
            }
            else
            {
                wizardActiveTab = 0;
            }
        }
        catch(Exception exception)
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, exception);
            ExceptionUtil.handleException(null, exception);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToApplicationRenewalServiceApplicationSelectionView());
        }
    }

    public int getWizardActiveTab() {
        return wizardActiveTab;
    }

    public void setWizardActiveTab(int wizardActiveTab) {
        this.wizardActiveTab = wizardActiveTab;
    }

    public ApplicationCreationDependBean getApplicationCreationDependBean() {
        return applicationCreationDependBean;
    }

    public void setApplicationCreationDependBean(ApplicationCreationDependBean applicationCreationDependBean) {
        this.applicationCreationDependBean = applicationCreationDependBean;
    }

    public CVCreationDependBean getCVCreationDependBean() {
        return cVCreationDependBean;
    }

    public void setCVCreationDependBean(CVCreationDependBean cVCreationDependBean) {
        this.cVCreationDependBean = cVCreationDependBean;
    }

    public ProgressReportCreationDependBean getProgressReportCreationDependBean() {
        return progressReportCreationDependBean;
    }

    public void setProgressReportCreationDependBean(ProgressReportCreationDependBean progressReportCreationDependBean) {
        this.progressReportCreationDependBean = progressReportCreationDependBean;
    }
    
    public void goBack()
    {
        if(wizardActiveTab > 1 && wizardActiveTab <=  MAX_TAB_INDEX)
        {
            wizardActiveTab--;
        }
    }
    
    public void completeFinalProgressReport()
    {
        try
        {            
            applicationRenewalServiceLocal.createFinalProgressReportForApplication(sessionManagerBean.getSession(), oldApplication, progressReportCreationDependBean.getCombinedProgressReport());
            wizardActiveTab = 1;
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public void updateCV()
    {
        try
        {            
            applicationRenewalServiceLocal.updateResearchFellowCV(sessionManagerBean.getSession(),cVCreationDependBean.getCombinedCv());
            wizardActiveTab = 2;
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }    
    
    public void completeApplicationRenewal()
    {
        try 
        {            
            applicationRenewalServiceLocal.createRenewalApplication(sessionManagerBean.getSession(), oldApplication, applicationCreationDependBean.getCombinedApplication());
            wizardActiveTab = 3;
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public String submitApplicationRenewal()
    {
        try 
        {            
            applicationRenewalServiceLocal.submitApplication(sessionManagerBean.getSession(), applicationCreationDependBean.getCombinedApplication());
            return navigationManagerBean.goToApplicationRenewalServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationRenewalBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
    
}
