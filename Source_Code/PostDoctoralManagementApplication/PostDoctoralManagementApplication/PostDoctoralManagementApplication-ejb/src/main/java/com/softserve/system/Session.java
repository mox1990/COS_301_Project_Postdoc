/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class Session {
    
    private HttpSession session = null;
    private Person user = null;
    private boolean systemLevel = false;

    public Session(HttpSession Session, Person User) 
    {
        user = User;
        session = Session;
        systemLevel = false;
    }
    
    public Session(HttpSession Session, Person User, Boolean sysLevel) 
    {
        user = User;
        session = Session;
        systemLevel = sysLevel;
    }
    
    public HttpSession getHttpSession()
    {
        return session;
    }
    
    public Person getUser()
    {
        return user;
    }
    
    public String getHttpSessionUsername()
    {
        return (String) session.getAttribute("username");
    }
    
    public void setHttpSessionUsername(String username)
    {
        session.setAttribute("username", username);
    }
    
    public String getHttpSessionPassword()
    {
        return (String) session.getAttribute("password");
    }
    
    public void setHttpSessionPassword(String password)
    {
        session.setAttribute("password", password);
    }
    
    public Boolean getLoggedInStatus()
    {
        return (Boolean) session.getAttribute("status");
    }
    
    public void setLoggedInStatus(Boolean status)
    {
        session.setAttribute("status",status);
    }
    
    public boolean isSystem()
    {
        return systemLevel;
    }
    
    public boolean doesHttpSessionUsernameMatchUserUsername()
    {
        return (user.getSystemID().toLowerCase().equals(getHttpSessionUsername().toLowerCase()));
    }
    
    public boolean doesHttpSessionUsernameMatchUserEmail()
    {
        return (user.getEmail().toLowerCase().equals(getHttpSessionUsername().toLowerCase()));
    }
    
    public boolean doesHttpSessionPasswordMatchUserPassword()
    {
        return (user.getPassword().equals(getHttpSessionPassword()));
    }
    
    public boolean doesUserHaveSecurityRole(int roleID)
    {
        return doesUserHaveSecurityRole(new SecurityRole((long) roleID));
    }
    
    public boolean doesUserHaveSecurityRole(SecurityRole securityRole)
    {
        return user.getSecurityRoleList().contains(securityRole);
    }
    
    public boolean doesUserHaveAnyOfTheseSecurityRole(ArrayList<SecurityRole> securityRoles)
    {
       for(SecurityRole sr: securityRoles)
       {
           if(doesUserHaveSecurityRole(sr))
           {
               return true;
           }
       }
       return false;
    }
    
    public boolean isUserAccountDisabled()
    {
        if(isSystem())
        {
            return true;
        }
        else
        {
            if(user == null || user.getAccountStatus() == null)
            {
                return false;
            }
            
            return user.getAccountStatus().equals(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
        }
                
        
    }
}
