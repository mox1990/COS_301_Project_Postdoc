/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Webapp.conversationbeans.conversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "newUserAccountCreationRequestBean")
@RequestScoped
public class NewUserAccountCreationRequestBean {

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private conversationManagerBean conversationManagerBean;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private UIComponent errorContainer;
    
    private Person person;
    private Address address;
    private UpEmployeeInformation employeeInformation;
    private Address upAddress;
    
    private List<SecurityRole> sourceRoles;
    private List<SecurityRole> targetRoles;
    private DualListModel<SecurityRole> securityRoles;
    
    /**
     * Creates a new instance of UserAccountCreationRequestBean
     */
    public NewUserAccountCreationRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        if(!conversationManagerBean.isConversationActive() || !conversationManagerBean.getConverseID().equals("post"))
        {
            person = new Person();
            conversationManagerBean.startConversation();
            conversationManagerBean.addObjectToStorage(person);
        }
        else
        {
            person = conversationManagerBean.getObjectFromStroage(0, Person.class);
        }
        
        address = new Address();
        employeeInformation = new UpEmployeeInformation();
        upAddress = new Address();
        
        sourceRoles = userAccountManagementServiceLocal.getAllSecurityRoles();
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
        
        if(this.securityRoles.getTarget().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR))
        {
            List<SecurityRole> tempList = this.securityRoles.getSource();
            this.securityRoles.getSource().clear();
            this.securityRoles.getTarget().addAll(tempList);                 
        }        
    }
                
    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }
    
    public String performProspectiveFellowUserAccountCreation()
    {        
        try 
        {
            person.setSecurityRoleList(new ArrayList<SecurityRole>());
            person.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
            if(employeeInformation.getEmployeeID().equals(""))
            {
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), false, person, address, null, null);
            }
            else
            {
                person.setSystemID(employeeInformation.getEmployeeID());
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), true, person, address, employeeInformation, upAddress);
            }
            
            return navigationManagerBean.goToPortalView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    public String performGeneralUserAccountCreationRequest()
    {
        try 
        {
            person.setSecurityRoleList(targetRoles);
            
            if(employeeInformation.getEmployeeID().equals(""))
            {
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), person.getUpEmployee(), person, address, null, null);
            }
            else
            {
                person.setSystemID(employeeInformation.getEmployeeID());
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), person.getUpEmployee(), person, address, employeeInformation, upAddress);
            }
            
            return navigationManagerBean.goToPreviousBreadCrumb();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    
}
