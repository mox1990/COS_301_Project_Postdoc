/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.Webapp.depenedentbeans.PersonFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import java.io.Serializable;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExceptionHandler;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "userAccountsViewerBean")
@ConversationScoped
public class UserAccountsViewerBean implements Serializable {
    
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
     * Creates a new instance of UserAccountsViewerRequestBean
     */
    public UserAccountsViewerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            personFilterDependBean.init(userAccountManagementServiceLocal.viewAllUserAccounts(sessionManagerBean.getSession()));
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
            
    public void selectUserAccount(Person person)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("ACCOUNT",person);        
    }
    
    public String editUserAccount(Person person)
    {
        selectUserAccount(person);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToUserAccountManagementlAccountViewer();
    }
    
    public String removeUserAccount(Person person)
    {
        try 
        {
            userAccountManagementServiceLocal.removeUserAccount(sessionManagerBean.getSession(), person.getSystemID());
            sessionManagerBean.updateObjectInSessionStorageAt("ACCOUNT",person);
            return navigationManagerBean.goToUserAccountManagementAccountsViewer();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsViewerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
        return "";
    }
    
    public boolean isUserAccountDisabled(Person account)
    {
        return account.getAccountStatus().equals(com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
    }
    
    public boolean isUserAccountDorment(Person account)
    {
        return account.getAccountStatus().equals(com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_DORMENT);
    }
    
    public boolean isUserAccountActive(Person account)
    {
        return account.getAccountStatus().equals(com.softserve.auxiliary.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
    }
    
    public boolean isOwnerOfAccount(Person account)
    {
        try 
        {
            return sessionManagerBean.getSession().getUser().equals(account);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsViewerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return false;
        }
    }
    
    public boolean isSystemAdmin()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }  
    }
    
    public boolean isUserAccountRemovable(Person person)
    {
        return !isOwnerOfAccount(person) && (isUserAccountActive(person) || isUserAccountDorment(person));            
    }
}
