/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationProgressViewerServiceLocal;
import java.util.ArrayList;
import java.util.List;
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
@Named(value="applicationProgressViewerSelectionRequest")
@RequestScoped
public class ApplicationProgressViewerSelectionRequest {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @EJB
    private ApplicationProgressViewerServiceLocal applicationProgressViewerServiceLocal;
    
    public List<Application> getFellowApplications()
    {
        try 
        {
            return sessionManagerBean.getSession().getUser().getApplicationList1();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return new ArrayList<Application>();
        }
    }
    
    public List<Application> getGrantHolderApplications()
    {
        try 
        {
            return sessionManagerBean.getSession().getUser().getApplicationList2();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return new ArrayList<Application>();
        }
    }
    
    public List<Application> getAllApplications()
    {
        try 
        {
            return applicationProgressViewerServiceLocal.getAllApplications(sessionManagerBean.getSession());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return new ArrayList<Application>();
        }
    }
    
    public String viewApplication(Application application)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("APPLICATION",application);
        
        return navigationManagerBean.goToApplicationProgressViewerApplicationProgressView();
    }
    
    public boolean isFellowApplicationDisplayable()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
            
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
    public boolean isGrantHolderApplicationDisplayable()
    {
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveSecurityRole(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
    public boolean isAllApplicationDisplayable()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionRequest.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
}
