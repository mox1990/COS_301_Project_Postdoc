/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.depenedentbeans.ApplicationFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.DRISApprovalServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "drisApplicationSelectionBean")
@ConversationScoped
public class DRISApplicationSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBeanEndorsed;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBeanEligible;
    @Inject
    private Conversation conversation;

    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
    
    /**
     * Creates a new instance of HODApplicationSelectionRequestBean
     */
    public DRISApplicationSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            applicationFilterDependBeanEndorsed.init(dRISApprovalServiceLocal.loadPendingEndorsedApplications(sessionManagerBean.getSession(), 0, Integer.MAX_VALUE));
            applicationFilterDependBeanEligible.init(dRISApprovalServiceLocal.loadPendingEligibleApplications(sessionManagerBean.getSession(), 0, Integer.MAX_VALUE));
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(null, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }

    public ApplicationFilterDependBean getApplicationFilterDependBeanEligible() {
        return applicationFilterDependBeanEligible;
    }

    public ApplicationFilterDependBean getApplicationFilterDependBeanEndorsed() {
        return applicationFilterDependBeanEndorsed;
    }
            
    public void selectApplication(Application application)
    {
        sessionManagerBean.addObjectToSessionStorage("APPLICATION",application);
    }
    
    public String viewApplication(Application application)
    {
        selectApplication(application);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToDRISApprovalServiceApplicationViewer();
    }
}
