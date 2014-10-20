/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.CV.*;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.ejb.applicationservices.ApplicationSuccessEvaluationServicesLocal;
import com.softserve.ejb.applicationservices.HODRecommendationServices;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationViewerRequestBean")
@RequestScoped
public class ApplicationViewerRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ApplicationSuccessEvaluationServicesLocal applicationSuccessEvaluationServicesLocal;
    
    /**
     * Creates a new instance of HODApplicationViewerRequestBean
     */
    public ApplicationViewerRequestBean() {
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }
        
    public boolean isDRISMember()
    {
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveSecurityRole(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        } 
        catch (Exception ex) 
        {
            return false;
        }
    }
    
    public boolean isDeanOrDRISMember()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }
    }
    
    public boolean isHODOrDeanOrDRISMember()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
            securityRoles.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_HOD);
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        }
    }
    
    public String editProgressReport(ProgressReport progressReport)
    {
        sessionManagerBean.addObjectToSessionStorage("PROGRESSREPORT", progressReport);
                
        return navigationManagerBean.goToProgressReportManagementServiceReportUpdateView();
    }
    
    public String getSuccessRateOfSelectedApplication()
    {
        try 
        {
            return Double.toString(Math.floor(applicationSuccessEvaluationServicesLocal.getApplicationSuccessRating(sessionManagerBean.getSession(), getSelectedApplication()) * 100 )) + "%";
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ApplicationViewerRequestBean.class, ex);
            return "No rating available at this time";
        }
    }
}
