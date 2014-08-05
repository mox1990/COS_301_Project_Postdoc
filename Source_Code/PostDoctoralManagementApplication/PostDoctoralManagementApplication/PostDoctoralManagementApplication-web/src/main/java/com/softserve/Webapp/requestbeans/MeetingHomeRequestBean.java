/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingHomeRequestBean")
@RequestScoped
public class MeetingHomeRequestBean {

    @Inject
    private SessionManagerBean sessionManagerBean; 
    
    /**
     * Creates a new instance of MeetingHomeRequestBean
     */
    public MeetingHomeRequestBean() {
    }
    
    public boolean isMeetingCreationDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (AuthenticationException ex) 
        {
            return false;
        }        
    }
    
    public boolean isMeetingSelectionDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (AuthenticationException ex) 
        {
            return false;
        }        
    }
    
    public boolean isCommentatorDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (AuthenticationException ex) 
        {
            return false;
        }        
    }
}
