/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import auto.softserve.XMLEntities.CV.ProgressReportContent;
import auto.softserve.XMLEntities.CV.Reference;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Webapp.depenedentbeans.ProgressReportCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.ProgressReportManagementServiceLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "progressReportCreationBean")
@ConversationScoped
public class ProgressReportCreationBean implements Serializable{
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject 
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private ProgressReportCreationDependBean progressReportCreationDependBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    
    
    
    /**
     * Creates a new instance of ProgressReportCreationRequestBean
     */
    public ProgressReportCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            if(getSelectedApplication() == null)
            {
                throw new Exception("No application selected");
            }
            progressReportCreationDependBean.init();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ProgressReportCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToProgressReportManagementServiceApplicationSelectionView());
        }
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
            ExceptionUtil.logException(ProgressReportCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
}
