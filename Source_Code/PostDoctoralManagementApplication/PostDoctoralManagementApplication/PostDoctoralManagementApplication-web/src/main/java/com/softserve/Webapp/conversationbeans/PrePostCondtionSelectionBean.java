/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.depenedentbeans.PersonFilterDependBean;
import com.softserve.Webapp.depenedentbeans.PrePostConditionMethodFilterBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.PrePostConditionalManagementServicesLocal;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "prePostCondtionSelectionBean")
@ConversationScoped
public class PrePostCondtionSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private PrePostConditionMethodFilterBean prePostCondtionMethodFilterBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private PrePostConditionalManagementServicesLocal prePostConditionalManagementServicesLocal;
    
    /**
     * Creates a new instance of PrePostCondtionSelectionBean
     */
    public PrePostCondtionSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            prePostCondtionMethodFilterBean.init(prePostConditionalManagementServicesLocal.loadPrePostConditionalMethods(sessionManagerBean.getSession()));
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(PendingUserAccountsSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToUserAccountManagementServicesHomeView());
        }
    }

    public PrePostConditionMethodFilterBean getPrePostCondtionMethodFilterBean() {
        return prePostCondtionMethodFilterBean;
    }
    
    public String editPrePostCondition(PrePostConditionMethod prePostCondtionMethod)
    {
        try
        {
            sessionManagerBean.addObjectToSessionStorage("PREPOSTCONDITION", prePostCondtionMethod);
            return navigationManagerBean.goToPrePostConditionManagementServicesEditorView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(PrePostCondtionSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
