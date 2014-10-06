/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Person;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "ondemandUserAccountRequestBean")
@RequestScoped
public class ondemandUserAccountRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
        
    private Person userAccountFound;
    
    /**
     * Creates a new instance of ondemandUserAccountRequestBean
     */
    public ondemandUserAccountRequestBean() 
    {
    }
    
    @PostConstruct
    public void init()
    {
        if(!sessionManagerBean.doesKeyExistInSessionStorage("USER_EXIST"))
        {
            sessionManagerBean.addObjectToSessionStorage("USER_EXIST", Boolean.FALSE);
        }
    }
    
    public boolean doesUserAccountExist()
    {   
        Boolean b = sessionManagerBean.getObjectFromSessionStorage("USER_EXIST", Boolean.class); 
        
        if(b == null)
        {
            return false;
        }
        else
        {
            return b;
        }
    }
    
    public Person findUserAccount(String email, Person updatablePerson)
    {
        userAccountFound = userAccountManagementServiceLocal.getUserBySystemIDOrEmail(email);
        
        if(userAccountFound != null)
        {
            System.out.println("=========User found");
            MessageUtil.CreateGlobalFacesMessage("User account found!", "The user account with specified email has been found in our databases.", FacesMessage.SEVERITY_INFO);
            sessionManagerBean.updateObjectInSessionStorageAt("USER_EXIST", Boolean.TRUE);
            return userAccountFound;
        }
        else
        {
            System.out.println("=========User not found");
            sessionManagerBean.updateObjectInSessionStorageAt("USER_EXIST", Boolean.FALSE);
            
            Person person = new Person();
            
            person.setEmail(updatablePerson.getEmail());
            person.setFullName(updatablePerson.getFullName());
            person.setSurname(updatablePerson.getSurname());
            person.setTitle(updatablePerson.getTitle());
            person.setSystemID("");
            person.setUpEmployee(false);
            System.out.println("person title " + person.getTitle());
            
            return person;
        }
    }
}
