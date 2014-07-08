/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBEntities.Person;
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
    }
    
    public Session(HttpSession Session, Person User, Boolean sysLevel) 
    {
        user = User;
        session = Session;
        systemLevel = sysLevel;
    }
    
    public HttpSession getSession()
    {
        return session;
    }
    
    public Person getUser()
    {
        return user;
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
}
