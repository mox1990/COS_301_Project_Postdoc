/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Webapp.depenedentbeans.LocationFinderDependBean;
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
    private LocationFinderDependBean locationFinderDependBean;
    
    @Inject
    private Conversation conversation;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private String reTypePassword;
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
        
        person = sessionManagerBean.getObjectFromSessionStorage("ACCOUNT", Person.class);
        
        address = person.getAddressLine1();
        
        if(person.getUpEmployee())
        {
            employeeInformation = person.getEmployeeInformation();
            upAddress = person.getEmployeeInformation().getPhysicalAddress(); 
            locationFinderDependBean.init(employeeInformation.getDepartment());
        }
        else
        {
            employeeInformation = new EmployeeInformation();
            upAddress = new Address();
            employeeInformation.setDepartment(new Department((long)(0)));
            employeeInformation.getDepartment().setFaculty(new Faculty((long) 0));
            employeeInformation.getDepartment().getFaculty().setInstitution(new Institution((long)(0)));
            locationFinderDependBean.init(null);
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

    public String getReTypePassword() {
        return reTypePassword;
    }

    public void setReTypePassword(String reTypePassword) {
        this.reTypePassword = reTypePassword;
    }
        
    public LocationFinderDependBean getLocationFinderDependBean() {
        return locationFinderDependBean;
    }

    public void setLocationFinderDependBean(LocationFinderDependBean locationFinderDependBean) {
        this.locationFinderDependBean = locationFinderDependBean;
    }
            
    public String performGeneralUserAccountEditRequest()
    {
        try 
        {
            if(!reTypePassword.equals(person.getPassword()))
            {
                throw new Exception("Passwords do not match");
            }
            
            if(isSystemAdmin)
            {
                securityRoles.getTarget().addAll(securityRoles.getSource()); 
                securityRoles.getTarget().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            }

            person.setSecurityRoleList(new ArrayList<SecurityRole>());
            person.getSecurityRoleList().addAll(securityRoles.getTarget());
            
            System.out.println(person.getSecurityRoleList().toString());
            
            person.setAddressLine1(address);
            if(person.getUpEmployee())
            {
                employeeInformation.setDepartment(locationFinderDependBean.getActualDepartmentEntity(employeeInformation.getDepartment().getDepartmentID()));
                employeeInformation.setEmployeeID(person.getSystemID());
                employeeInformation.setPhysicalAddress(upAddress);
                person.setEmployeeInformation(employeeInformation);
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), person);               
            }
            else
            {
                userAccountManagementServiceLocal.updateUserAccount(sessionManagerBean.getSystemLevelSessionForCurrentSession(), person);
            }
            
            sessionManagerBean.clearSessionStorage();
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToPreviousBreadCrumb();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(UserAccountsGeneralAccountEditBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
