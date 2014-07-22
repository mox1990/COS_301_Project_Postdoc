/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.conversationbeans.conversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@Named(value = "userAccountsViewerRequestBean")
@RequestScoped
public class UserAccountsViewerRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private conversationManagerBean conversationManagerBean;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private UIComponent errorContainer;
    
    /**
     * Creates a new instance of UserAccountsViewerRequestBean
     */
    public UserAccountsViewerRequestBean() {
    }
    
    public List<Person> getUserAccounts()
    {
        try
        {            
            List<Person> accounts = userAccountManagementServiceLocal.viewAllUserAccounts(sessionManagerBean.getSession());
            System.out.println("Number of accounts " + accounts.size());
            return accounts;
        }
        catch(Exception ex)
        {
            System.out.println("Exception");
            ExceptionUtil.handleException(errorContainer, ex);
            return new ArrayList<Person>();
        }
    }
    
    public void selectUserAccount(Person person)
    {
        
        if(!conversationManagerBean.isConversationActive())
        {
            System.out.println("Stopping converse ");
            conversationManagerBean.stopConversation();
        }
        System.out.println("Starting converse ");
        conversationManagerBean.startConversation();
        System.out.println("Before Add ");
        conversationManagerBean.clearConversationStorage();
        conversationManagerBean.addObjectToStorage(person);        
    }
    
    public String editUserAccount(Person person)
    {
        selectUserAccount(person);
        return navigationManagerBean.goToUserAccountManagementlAccountViewer();
    }
    
    public String removeUserAccount(Person person)
    {
        try 
        {
            userAccountManagementServiceLocal.removeUserAccount(sessionManagerBean.getSession(), person.getSystemID());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex);
        }
        
        return "";
    }
    
    public boolean isUserAccountDisabled(Person account)
    {
        return account.getAccountStatus().equals(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
    }
}
