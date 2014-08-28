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
import com.softserve.ejb.UserAccountManagementServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "prospectiveUserAccountCreationBean")
@ConversationScoped
public class ProspectiveUserAccountCreationBean implements Serializable{

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    @Inject
    private LocationFinderDependBean locationFinderDependBean;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private UIComponent errorContainer;
    
    private Person person;
    private Address address;
    private EmployeeInformation employeeInformation;
    private Address upAddress;
    
    /**
     * Creates a new instance of UserAccountCreationRequestBean
     */
    public ProspectiveUserAccountCreationBean() {
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
        employeeInformation.setDepartment(new Department((long)(0)));
        employeeInformation.getDepartment().setFaculty(new Faculty((long) 0));
        employeeInformation.getDepartment().getFaculty().setInstitution(new Institution((long)(0)));

        locationFinderDependBean.init(null);    
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
                
    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }

    public LocationFinderDependBean getLocationFinderDependBean() {
        return locationFinderDependBean;
    }

    public void setLocationFinderDependBean(LocationFinderDependBean locationFinderDependBean) {
        this.locationFinderDependBean = locationFinderDependBean;
    }
    
    public String performProspectiveFellowUserAccountCreation()
    {        
        try 
        {
            person.setSecurityRoleList(new ArrayList<SecurityRole>());
            person.getSecurityRoleList().add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
            
            person.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
            person.setAddressLine1(address);
            
            if(person.getUpEmployee())
            {
                employeeInformation.setDepartment(locationFinderDependBean.getActualDepartmentEntity(employeeInformation.getDepartment().getDepartmentID()));
                person.setSystemID(employeeInformation.getEmployeeID());
                employeeInformation.setPhysicalAddress(upAddress);
                person.setEmployeeInformation(employeeInformation);

                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), false, person);               
            }
            else
            {
                userAccountManagementServiceLocal.createUserAccount(sessionManagerBean.getSystemLevelSession(), false, person);            
            }
            
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToPortalView();
        }
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ProspectiveUserAccountCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    } 
}
