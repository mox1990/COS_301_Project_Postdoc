/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import com.softserve.ejb.*;
import com.softserve.system.Session;
import java.io.Serializable;
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
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    @EJB
    UserGatewayLocal userGateway;
    
    private String username = "";
    private String password = "";
    
    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    
    
    public String login()
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        if(httpSession == null)
        {
            httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true));
            
        }
        
        httpSession.setAttribute("systemID", username);
        httpSession.setAttribute("password", password);
        httpSession.setAttribute("status", Boolean.FALSE);
        
        System.out.println("Test " + httpSession.getAttribute("systemID"));
        
        try
        {
            Session session = userGateway.login(httpSession);
            return "welcome";
            
        }
        catch(Exception ex)
        {
            return "An error occured ";         
        }
    }
    
}
