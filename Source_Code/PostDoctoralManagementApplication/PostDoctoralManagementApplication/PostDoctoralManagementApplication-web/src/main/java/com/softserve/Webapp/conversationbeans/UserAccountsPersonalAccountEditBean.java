/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "userAccountsPersonalAccountEditBean")
@ConversationScoped
public class UserAccountsPersonalAccountEditBean implements Serializable {

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    
    @Inject
    private Conversation conversation;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private UIComponent errorContainer;
    
    private Person person;
    private Address address;
    private UpEmployeeInformation employeeInformation;
    private Address upAddress;
    
    /**
     * Creates a new instance of UserAccountCreationRequestBean
     */
    public UserAccountsPersonalAccountEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        try 
        {
            conversationManagerBean.registerConversation(conversation);
            conversationManagerBean.startConversation(conversation);
            
            person = sessionManagerBean.getSession().getUser();
            
            address = person.getAddressLine1();
            
            if(person.getUpEmployee())
            {
                employeeInformation = person.getUpEmployeeInformation();
                upAddress = person.getUpEmployeeInformation().getPhysicalAddress();
            }
            else
            {
                employeeInformation = new UpEmployeeInformation();
                upAddress = new Address();
            }
        } 
        catch (AuthenticationException ex) 
        {
            ExceptionUtil.logException(UserAccountsPersonalAccountEditBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
        
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UpEmployeeInformation getEmployeeInformation() {
        return employeeInformation;
    }

    public void setEmployeeInformation(UpEmployeeInformation employeeInformation) {
        this.employeeInformation = employeeInformation;
    }

    public Address getUpAddress() {
        return upAddress;
    }

    public void setUpAddress(Address upAddress) {
        this.upAddress = upAddress;
    }

    
    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }
        
    public String performPersonalUserAccountEditRequest()
    {
        try 
        {
            
            if(person.getUpEmployee())
            {
                person.setSystemID(employeeInformation.getEmployeeID());
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSession(), person, address, employeeInformation, upAddress);               
            }
            else
            {
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSession(), person, address, null, null);
            }
            
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToPreviousBreadCrumb();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
}
