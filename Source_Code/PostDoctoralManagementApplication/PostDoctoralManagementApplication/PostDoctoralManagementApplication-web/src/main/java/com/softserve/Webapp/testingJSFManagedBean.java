package com.softserve.Webapp;

/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.ejb.UserAccountManagementServicesLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "testingJSFManagedBean")
@SessionScoped
public class testingJSFManagedBean implements Serializable {
    
    
    @EJB
    private UserAccountManagementServicesLocal management;
    private String systemID;
    
    /**
     * Creates a new instance of testingJSFManagedBean
     */
    public testingJSFManagedBean() {
        
    }
    
    public String getSystemID()
    {
        return systemID;
    }
    public void setSystemID(String val)
    {
       systemID = val;
    }
    
    public void createAddressessTest()
    {

        management.testAddresses();
        
    }
    
    
    public void createPerson()
    {
        Person person = new Person(systemID, "Check", "Mr", "Mathys", "Ellis", "mox.1990@gmail.vom", false);
        person.setCellphoneNumber("08370348568");
        
        Address address = new Address();        
        address.setCountry("South Africa");
        address.setProvince("MP");
        address.setZippostalCode("1200");        
        
        try 
        {
            management.createUserAccount(new Session((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false), person), true, person, address, null);
            
        } catch (Exception ex) {
            Logger.getLogger(testingJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public List<Person> getPeopleList()
    {
        List<Person> people = management.viewAllUserAccounts(new Session((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false), null));
        
        return people;
    }
    
}
