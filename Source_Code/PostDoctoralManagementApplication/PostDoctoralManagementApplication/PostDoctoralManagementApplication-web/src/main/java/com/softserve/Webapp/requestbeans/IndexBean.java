/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "indexBean")
@RequestScoped
public class IndexBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    
    private UIComponent errorContainer;
    private String usernameOrEmail = "";
    private String password = "";    
    
    /**
     * Creates a new instance of indexBean
     */
    public IndexBean() {
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }
    
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String performLoginRequest()
    {
        String l = sessionManagerBean.login(errorContainer,usernameOrEmail,password);
        System.out.println("====== " + l);
        return l;
    }
}
