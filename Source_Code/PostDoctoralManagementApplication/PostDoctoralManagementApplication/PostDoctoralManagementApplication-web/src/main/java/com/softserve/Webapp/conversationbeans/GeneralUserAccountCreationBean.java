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
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "generalUserAccountCreationBean")
@ConversationScoped
public class GeneralUserAccountCreationBean implements Serializable{

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
    
    private boolean isSystemAdmin;
    
    private List<SecurityRole> sourceRoles;
    private List<SecurityRole> targetRoles;
    private DualListModel<SecurityRole> securityRoles;
    
    /**
     * Creates a new instance of UserAccountCreationRequestBean
     */
    public GeneralUserAccountCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversation.begin();
        
        person = new Person();  
        address = new Address();
        employeeInformation = new UpEmployeeInformation();
        upAddress = new Address();
        
        sourceRoles = userAccountManagementServiceLocal.getAllSecurityRoles();
        sourceRoles.remove(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        
        targetRoles = new ArrayList<SecurityRole>();
        securityRoles = new DualListModel<SecurityRole>(sourceRoles, targetRoles);
        
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
        
    public String performGeneralUserAccountCreationRequest()
    {
        try 
        {
            if(isSystemAdmin)
            {
                targetRoles.addAll(sourceRoles);
                targetRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            }
            
            person.setSecurityRoleList(targetRoles);
            
            person.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
            
            if(person.getUpEmployee())
            {
                person.setSystemID(employeeInformation.getEmployeeID());
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), false, person, address, employeeInformation, upAddress);               
            }
            else
            {
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), false, person, address, null, null);            
            }
            
            sessionManagerBean.clearSessionStroage();
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
