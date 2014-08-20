/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.LocationManagementServiceLocal;
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
    @EJB
    private LocationManagementServiceLocal locationManagementServiceLocal;
    
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
    public GeneralUserAccountCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        person = new Person();  
        person.setTitle("Mr.");
        address = new Address();
        employeeInformation = new EmployeeInformation();
        upAddress = new Address();
        employeeInformation.setDepartment(new Department());
        
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
        
    public String performGeneralUserAccountCreationRequest()
    {
        try 
        {
            if(isSystemAdmin)
            {
                securityRoles.getTarget().addAll(securityRoles.getSource());
                
                
                securityRoles.getTarget().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            }
            System.out.println(securityRoles.getSource().toString());
            System.out.println(securityRoles.getTarget().toString());

            person.setSecurityRoleList(new ArrayList<SecurityRole>());
            person.getSecurityRoleList().addAll(securityRoles.getTarget());
            
            System.out.println(person.getSecurityRoleList().toString());
            
            person.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
            person.setAddressLine1(address);
            
            
            if(person.getUpEmployee())
            {
                person.setSystemID(employeeInformation.getEmployeeID());
                employeeInformation.setPhysicalAddress(address);
                person.setEmployeeInformation(employeeInformation);
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), true, person);               
            }
            else
            {
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), false, person);            
            }
            System.out.println("================================= about to clear");
            sessionManagerBean.clearSessionStorage();
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToUserAccountManagementServicesHomeView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(GeneralUserAccountCreationBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
}
