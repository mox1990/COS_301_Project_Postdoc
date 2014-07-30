/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Named(value = "userAccountsGeneralAccountEditBean")
@ConversationScoped
public class UserAccountsGeneralAccountEditBean implements Serializable {

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
    private EmployeeInformation employeeInformation;
    private Address upAddress;
    
    private boolean isSystemAdmin;
    
    private List<SecurityRole> sourceRoles;
    private List<SecurityRole> targetRoles;
    private DualListModel<SecurityRole> securityRoles;
    
    /**
     * Creates a new instance of UserAccountCreationRequestBean
     */
    public UserAccountsGeneralAccountEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        person = sessionManagerBean.getObjectFromSessionStroage(0, Person.class); 
        if(person == null)
        {
            System.out.println("==========Is null");
        }
        else
        {
            System.out.println("==========Is not null");
        }
        address = person.getAddressLine1();
        
        if(person.getUpEmployee())
        {
            employeeInformation = person.getEmployeeInformation();
            upAddress = person.getEmployeeInformation().getPhysicalAddress();            
        }
        else
        {
            employeeInformation = new EmployeeInformation();
            upAddress = new Address();
        }
        employeeInformation.setEmployeeID(person.getSystemID());
        
        sourceRoles = userAccountManagementServiceLocal.getAllSecurityRoles();        
        sourceRoles.remove(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        
        targetRoles = person.getSecurityRoleList();
        sourceRoles.removeAll(targetRoles);

        securityRoles = new DualListModel<SecurityRole>(sourceRoles, targetRoles); 
        
        if(targetRoles.remove(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR))
        {
            isSystemAdmin = true;            
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

    public EmployeeInformation getEmployeeInformation() {
        return employeeInformation;
    }

    public void setEmployeeInformation(EmployeeInformation employeeInformation) {
        this.employeeInformation = employeeInformation;
    }

    public Address getUpAddress() {
        return upAddress;
    }

    public void setUpAddress(Address upAddress) {
        this.upAddress = upAddress;
    }

    public DualListModel<SecurityRole> getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(DualListModel<SecurityRole> securityRoles) 
    {        
        this.securityRoles = securityRoles;      
    }

    public void setIsSystemAdmin(boolean isSystemAdmin) {
        this.isSystemAdmin = isSystemAdmin;
    }

    public boolean isIsSystemAdmin() {
        return isSystemAdmin;
    }
    
    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }
        
    public String performGeneralUserAccountEditRequest()
    {
        try 
        {
            if(isSystemAdmin)
            {                
                targetRoles.addAll(sourceRoles);
                sourceRoles.clear();
                targetRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            }
            
            person.setSecurityRoleList(targetRoles);
            person.setAddressLine1(address);
            if(person.getUpEmployee())
            {
                employeeInformation.setEmployeeID(person.getSystemID());
                employeeInformation.setPhysicalAddress(address);
                person.setEmployeeInformation(employeeInformation);
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), person);               
            }
            else
            {
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), person);
            }
            
            sessionManagerBean.clearSessionStroage();
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToPreviousBreadCrumb();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsGeneralAccountEditBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
}
