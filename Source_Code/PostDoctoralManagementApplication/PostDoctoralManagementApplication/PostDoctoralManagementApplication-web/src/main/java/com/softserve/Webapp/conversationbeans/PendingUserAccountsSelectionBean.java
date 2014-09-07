/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Person;
import com.softserve.Webapp.depenedentbeans.ApplicationFilterDependBean;
import com.softserve.Webapp.depenedentbeans.PersonFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
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
@Named(value = "pendingUserAccountsSelectionBean")
@ConversationScoped
public class PendingUserAccountsSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private PersonFilterDependBean personFilterDependBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    /**
     * Creates a new instance of UserAccountsSelectionBean
     */
    public PendingUserAccountsSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            personFilterDependBean.init(userAccountManagementServiceLocal.loadAllPendingOnDemandAccounts(sessionManagerBean.getSession()));
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(PendingUserAccountsSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToUserAccountManagementServicesHomeView());
        }
    }

    public PersonFilterDependBean getPersonFilterDependBean() {
        return personFilterDependBean;
    }
    
    public String approveOndemandAccount(Person person)
    {
        try 
        {
            userAccountManagementServiceLocal.approveOnDemandAccount(sessionManagerBean.getSession(), person);
            conversationManagerBean.deregisterConversation(conversation);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsViewerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
        return "";
    }
    
    public String declineOndemandAccount(Person person)
    {
        try 
        {
            userAccountManagementServiceLocal.declineOnDemandAccount(sessionManagerBean.getSession(), person);
            conversationManagerBean.deregisterConversation(conversation);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsViewerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
        return "";
    }
    
}
