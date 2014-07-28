/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Person;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.UserAccountManagementServiceLocal;
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
    public ondemandUserAccountRequestBean() {
    }
    
    public boolean doesUserAccountExist()
    {
        return userAccountFound != null; 
    }
    
    public Person findUserAccount(String email, Person updatablePerson)
    {
        userAccountFound = userAccountManagementServiceLocal.getUserBySystemIDOrEmail(email);
        
        if(doesUserAccountExist())
        {
            MessageUtil.CreateGlobalFacesMessage("User account found!", "The user account with specified email has been found in our databases.", FacesMessage.SEVERITY_INFO);
            return userAccountFound;
        }
        else
        {
            return new Person();
        }
    }
}
