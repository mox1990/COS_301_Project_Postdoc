/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.*;
import com.softserve.system.Session;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "sessionManagerBean")
@SessionScoped
public class SessionManagerBean implements Serializable {

    @EJB
    private UserGatewayLocal userGateway;
    
    /**
     * Creates a new instance of LoginManagedBean
     */
    public SessionManagerBean() {
    }
    
    public String login(UIComponent errorMessageComponent, String username, String password)
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        if(httpSession == null)
        {
            httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true));            
        }
        
        httpSession.setAttribute("username",username);
        httpSession.setAttribute("password",password);
        httpSession.setAttribute("status", Boolean.FALSE);
        
        try
        {
            Session session = userGateway.getSessionFromHttpSession(httpSession);
        
            System.out.println("Test " + httpSession.getAttribute("username"));
            
            userGateway.login(session);
            return "welcome";
        }
        catch(Exception ex)
        {
            //errorMessage = "User name and/or password is invalid";
            ExceptionUtil.handleException(errorMessageComponent, ex);
            return "";
        }
    }
    
    public String logout(UIComponent errorMessageComponent)
    {
        try 
        {   
            userGateway.logout(getSession());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorMessageComponent, ex);
            return "";
        }
        
        return "index";
    }
    
    public Session getSession() throws AuthenticationException
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        return userGateway.getSessionFromHttpSession(httpSession);
    }
}
