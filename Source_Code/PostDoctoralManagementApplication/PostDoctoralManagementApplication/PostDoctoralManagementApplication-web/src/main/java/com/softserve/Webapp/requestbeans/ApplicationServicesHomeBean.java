/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.persistence.DBEntities.SecurityRole;
import java.lang.Exception;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import java.util.ArrayList;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationServicesHomeBean")
@RequestScoped
public class ApplicationServicesHomeBean {

    @Inject
    private SessionManagerBean sessionManagerBean;
    
    /**
     * Creates a new instance of ApplicationServicesHomeBean
     */
    public ApplicationServicesHomeBean() {
    }
    
    public boolean isNewApplicationServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
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
    
    public boolean isRenewalApplicationServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
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
    
    public boolean isApplicationProgressViewerServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
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
    
    public boolean isRefereeReportServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
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
    
    public boolean isGrantHolderFinalisationServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
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
    
    public boolean isHODRecommendationServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
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
    
    public boolean isDeanEndorsementServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
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
    
    public boolean isDRISApprovalServiceDisplayable()
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
    
    public boolean isProgressReportMangementServiceDisplayable()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
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
    
    public boolean isForwardAndRewindServiceDisplayable()
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
}
