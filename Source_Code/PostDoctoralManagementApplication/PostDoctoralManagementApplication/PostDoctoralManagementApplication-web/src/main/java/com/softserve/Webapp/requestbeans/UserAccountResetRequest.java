/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "userAccountResetRequest")
@RequestScoped
public class UserAccountResetRequest {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    
    private String email;
    private String systemID;
    
    /**
     * Creates a new instance of UserAccountResetRequest
     */
    public UserAccountResetRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }
    
    
    
    public String resetAccount()
    {
        Person person = new Person();
        person.setSystemID(systemID);
        person.setEmail(email);
        
        try 
        {
            userAccountManagementServiceLocal.accountReset(person);
            return navigationManagerBean.goToPortalView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(UserAccountResetRequest.class, ex);
            return "";
        }
    }
    
}
