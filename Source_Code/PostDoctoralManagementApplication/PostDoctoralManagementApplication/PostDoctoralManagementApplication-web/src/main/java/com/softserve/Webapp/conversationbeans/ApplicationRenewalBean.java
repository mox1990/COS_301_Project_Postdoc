/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import auto.softserve.XMLEntities.CV.AdditionalInformation;
import auto.softserve.XMLEntities.CV.Item;
import auto.softserve.XMLEntities.CV.OtherContributions;
import auto.softserve.XMLEntities.CV.Reference;
import auto.softserve.XMLEntities.CV.ResearchOutput;
import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.application.Member;
import auto.softserve.XMLEntities.fellow.ProgressReportContent;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.depenedentbeans.ApplicationCreationDependBean;
import com.softserve.Webapp.depenedentbeans.CVCreationDependBean;
import com.softserve.Webapp.depenedentbeans.ProgressReportCreationDependBean;
import com.softserve.Webapp.requestbeans.ProgressReportCreationRequestBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationRenewalServiceLocal;
import com.softserve.ejb.ProgressReportManagementServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        oldApplication = sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class); 
        
        applicationCreationDependBean.init(new Application());
        cVCreationDependBean.init(oldApplication.getFellow().getCv());
        progressReportCreationDependBean.init();        
        
        if(applicationRenewalServiceLocal.doesApplicationHaveFinalProgressReport(oldApplication))
        {
            wizardActiveTab = 0;
        }
        else
        {
            wizardActiveTab = 1;
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

    public CVCreationDependBean getcVCreationDependBean() {
        return cVCreationDependBean;
    }

    public void setcVCreationDependBean(CVCreationDependBean cVCreationDependBean) {
        this.cVCreationDependBean = cVCreationDependBean;
    }

    public ProgressReportCreationDependBean getProgressReportCreationDependBean() {
        return progressReportCreationDependBean;
    }

    public void setProgressReportCreationDependBean(ProgressReportCreationDependBean progressReportCreationDependBean) {
        this.progressReportCreationDependBean = progressReportCreationDependBean;
    }
    
    public void completeFinalProgressReport()
    {
        try
        {            
            applicationRenewalServiceLocal.createFinalProgressReportForApplication(sessionManagerBean.getSession(), oldApplication, progressReportCreationDependBean.getCombinedProgressReport());
            wizardActiveTab++;
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
            wizardActiveTab++;
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
            wizardActiveTab++;
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
