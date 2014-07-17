/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

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
    public NewUserAccountCreationRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        person = new Person();
        address = new Address();
        employeeInformation = new UpEmployeeInformation();
        upAddress = new Address();
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
    
    public String performProspectiveFellowUserAccountCreation()
    {        
        System.out.println("Hi1");
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
            return "index?force-redirect=true";
        } 
        catch (Exception ex) 
        {
            
            System.out.println(ex.getMessage());
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    
}
