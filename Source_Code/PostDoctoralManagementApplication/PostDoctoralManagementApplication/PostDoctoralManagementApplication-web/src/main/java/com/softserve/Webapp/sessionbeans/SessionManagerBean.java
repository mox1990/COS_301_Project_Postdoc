/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.*;
import com.softserve.system.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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

    @Inject
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private UserGatewayLocal userGateway;
    
    private ArrayList<Object> sessionStroage;
    
    /**
     * Creates a new instance of LoginManagedBean
     */
    public SessionManagerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        sessionStroage = new ArrayList<Object>();
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
            
            return navigationManagerBean.goToWelcomeView();
        }
        catch(Exception ex)
        {
            System.out.println("Login exception");
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
            
            return navigationManagerBean.goToPortalView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorMessageComponent, ex);
            return navigationManagerBean.goToPortalView();
        }
    }
    
    public void resetSession(UIComponent component)
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        if(httpSession == null)
        {
            httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true));
            httpSession.setAttribute("username","");
            httpSession.setAttribute("password","");
            httpSession.setAttribute("status", Boolean.FALSE);
        }
        else
        {
            if(httpSession.getAttribute("status") != null && httpSession.getAttribute("status").equals(Boolean.TRUE))
            {
                logout(component);
            }            
        }
        
    }
    
    public Session getSession() throws AuthenticationException
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        return userGateway.getSessionFromHttpSession(httpSession);
    }
    
    public Session getSystemLevelSession()
    {
        return new Session(null, null, Boolean.TRUE);
    }
    
    public Session getSystemLevelSessionForCurrentSession() throws AuthenticationException
    {        
        Session session = getSession();
        
        return new Session(session.getHttpSession(), session.getUser(), Boolean.TRUE);
    }
    
    public void authoriseUserViewAccess(List<SecurityRole> allowedRoles) throws IOException
    {
        try
        {
            userGateway.authenticateUser(getSession(), allowedRoles);
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(navigationManagerBean.goToPortalView());
        }
    }    
    
    public <T> T getObjectFromSessionStroage(int index, Class<T> objectClass)
    {
        try
        {
            if(sessionStroage.get(index) == null)
            {
                System.out.println("==========Object in storage is null");
            }
            T temp = objectClass.cast(sessionStroage.get(index));
            if(temp == null)
            {
                System.out.println("==========Casted is null");
            }
            else
            {
                System.out.println("==========Casted is not null");
            }
            return temp;
        }
        catch(ClassCastException ex)
        {
            System.out.println("==========Cast exception");
            ExceptionUtil.logException(SessionManagerBean.class, ex);
            return null;
        }
    }
    
    public int addObjectToSessionStroage(Object object)
    {
        if(object == null)
        {
            System.out.println("==========Object in is null");
        }
        sessionStroage.add(object);
        if(sessionStroage.get(sessionStroage.size() - 1) == null)
        {
            System.out.println("==========Added Object in storage is null");
        }
        System.out.println("================Added to storage");
        return sessionStroage.size() - 1;
    }
    
    public void removeObjectFromSessionStroageAt(int index)
    {
        sessionStroage.remove(index);
    }
    
    public void clearSessionStroage()
    {
        sessionStroage.clear();
    }
    
    public void updateObjectInSessionStroageAt(int index, Object object)
    {
        if(index > -1 && sessionStroage.size() < index)
        {
            sessionStroage.set(index, object);
        }
    }
    
    public int countObjectsInSessionStroage()
    {
        return sessionStroage.size();
    }
}
