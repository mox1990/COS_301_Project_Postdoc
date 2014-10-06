/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.StorageItem;
import com.softserve.auxillary.Hashing;
import com.softserve.system.Session;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
    
    private ArrayList<StorageItem> sessionStorage;
    
    /**
     * Creates a new instance of LoginManagedBean
     */
    public SessionManagerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        sessionStorage = new ArrayList<StorageItem>();
    }
    
    public String login(String username, String password) throws NoSuchAlgorithmException
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        if(httpSession == null)
        {
            httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true));            
        }
        
        httpSession.setAttribute("username",username);
        httpSession.setAttribute("password",Hashing.hashInput(password));
        httpSession.setAttribute("status", Boolean.FALSE);
        
        try
        {
            Session session = userGateway.getSessionFromHttpSession(httpSession);
        
            System.out.println("Login: " + httpSession.toString());
            
            userGateway.login(session);
            
            System.out.println("Login: Account status = " + session.getUser().getAccountStatus());
            
            if(session.isUserAccountDorment())
            {
                return navigationManagerBean.goToUserAccountManagementServicesOnDemandUserActivationView();
            }
            else if(session.isUserAccountActive())
            {
                return navigationManagerBean.goToWelcomeView();
            }
            else
            {
                ExceptionUtil.handleException(null, new Exception("User account is not available"));
                return navigationManagerBean.goToPortalView();
            }
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(SessionManagerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
    public String logout()
    {
        try 
        {   
            Session session = getSession();            
            userGateway.logout(session);            
            session.getHttpSession().invalidate();
            return navigationManagerBean.goToPortalView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(SessionManagerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return navigationManagerBean.goToPortalView();
        }
    }
    
    public void resetSession()
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        if(httpSession != null)
        {
            if(httpSession.getAttribute("status") != null && httpSession.getAttribute("status").equals(Boolean.TRUE))
            {
                logout();
            }            
        }
        
    }
    
    public Session getSession() throws Exception
    {
        HttpSession httpSession = (HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        
        return userGateway.getSessionFromHttpSession(httpSession);
    }
    
    public Session getSystemLevelSession()
    {
        return new Session(null, null, Boolean.TRUE);
    }
    
    public Session getSystemLevelSessionForCurrentSession() throws Exception
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
    
    public <T> T getObjectFromSessionStorage(int index, Class<T> objectClass)
    {
        System.out.println("=============Session storage: Current contents " + sessionStorage.toString());
        try
        {
            T temp = null;
            
            if(index > -1 && index < sessionStorage.size())
            {
                if(sessionStorage.get(index).getObject() == null)
                {
                    System.out.println("=============Session storage: Object at index " + index + " with key " + sessionStorage.get(index).getKey() + " is null");
                }
                
                temp = objectClass.cast(sessionStorage.get(index).getObject());
                
                if(temp == null)
                {
                    System.out.println("=============Session storage: Object at index " + index + " with key " + sessionStorage.get(index).getKey() + " casted to null");
                }
            }
            else
            {
                System.out.println("=============Session storage: Index " + index + " is out of bounds");
            }

            return temp;
        }
        catch(ClassCastException ex)
        {
            System.out.println("=============Session storage: Cast exception");
            ExceptionUtil.logException(SessionManagerBean.class, ex);
            return null;
        }
    }
    
    public <T> T getObjectFromSessionStorage(String key, Class<T> objectClass)
    {
        int index = sessionStorage.indexOf(new StorageItem(key, null));

        return getObjectFromSessionStorage(index, objectClass);        
    }
    
    public boolean doesKeyExistInSessionStorage(String key)
    {
        return sessionStorage.contains(new StorageItem(key, null));
    }
    
    public int addObjectToSessionStorage(String key, Object object)
    {
        if(object == null)
        {
            System.out.println("=============Session storage: Object with key " + key + " to insert is null");
        }
        
        if(doesKeyExistInSessionStorage(key))
        {
            updateObjectInSessionStorageAt(key, object);
            
            return sessionStorage.indexOf(new StorageItem(key, null));            
        }
        else
        {
            boolean success = sessionStorage.add(new StorageItem(key, object));            
            
            if(success)
            {
                if(sessionStorage.get(sessionStorage.size() - 1) == null)
                {
                    System.out.println("=============Session storage: Object with key " + key + " was added but is null");
                }
                else
                {        
                    System.out.println("=============Session storage: Object with key " + key + " was added " + sessionStorage.get(sessionStorage.size() - 1).toString());
                }  
                
                return sessionStorage.size() - 1;
            }
            else
            {
                System.out.println("=============Session storage: Object with key " + key + " could not be added");
                
                return -1;
            }
            
            
        }
        
        
    }
    
    public void removeObjectFromSessionStorageAt(int index)
    {
        if(index > -1 && index < sessionStorage.size())
        {
            sessionStorage.remove(index);
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void clearSessionStorage()
    {
        sessionStorage.clear();
    }
    
    public void updateObjectInSessionStorageAt(int index, Object object)
    {
        if(index > -1 && index < sessionStorage.size())
        {

            StorageItem storageItem = sessionStorage.get(index);
            storageItem.setObject(object);
            
            sessionStorage.set(index, storageItem);
            System.out.println("=============Session storage: Object updated at index " + index + " with key " + storageItem.getKey());
        }
    }
    
    public void updateObjectInSessionStorageAt(String key, Object object)
    {
        int index = sessionStorage.indexOf(new StorageItem(key, null));
        
        updateObjectInSessionStorageAt(index, object);
    }
    
    public int countObjectsInSessionStroage()
    {
        return sessionStorage.size();
    }
}
