/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.persistence.DBEntities.SecurityRole;
import java.lang.Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "welcomeBean")
@RequestScoped
public class welcomeBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;    
    
    /**
     * Creates a new instance of welcomeBean
     */
    public welcomeBean() {
    }
    
    @PostConstruct
    private void init()
    {
        //try
       // {
       //     sessionManagerBean.authoriseUserViewAccess(com.softserve.constants.PersistenceConstants.getAllSecurityRoles());
       // }
       // catch(IOException ex)
       // {
        //    return;
        //}
       //         
    }
    
    public boolean isReportServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isDataImportExportServicesDisplayable()
    {
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveSecurityRole(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isAuditTrailServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isArchivalServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isAnnouncementServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isLocationServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isNeuralNetworkManagementServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
    public boolean isPrePostConditionalServicesDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }        
    }
    
}
