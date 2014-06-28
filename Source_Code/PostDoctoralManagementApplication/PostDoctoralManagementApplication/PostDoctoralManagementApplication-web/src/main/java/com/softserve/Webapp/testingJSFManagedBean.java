package com.softserve.Webapp;

/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.UserAccountManagementServices.UserAccountManagementServicesLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "testingJSFManagedBean")
@RequestScoped
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
    
    public void createPerson()
    {
        Person person = new Person(systemID, "Check", "Mr", "Mathys", "Ellis", "mox.1990@gmail.vom", false);
        person.setCellphoneNumber("08370348568");
        Address address = new Address();        
        address.setCountry("South Africa");
        address.setProvince("MP");
        address.setZippostalCode("1200");
        //address.setPersonList(new ArrayList<Person>());
        //address.getPersonList().add(person);
        
        
        try 
        {
            management.createUserAccount((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false), true, person, address, null);
            
        } catch (Exception ex) {
            Logger.getLogger(testingJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public List<Person> getPeopleList()
    {
        List<Person> people = management.viewAllUserAccounts((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        return people;
    }
    
}
