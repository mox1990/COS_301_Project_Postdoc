/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.CV.*;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.DRISApprovalServiceLocal;
import com.softserve.ejb.HODRecommendationServices;
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
@Named(value = "drisApplicationViewerRequestBean")
@RequestScoped
public class DRISApplicationViewerRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
        
    private UIComponent errorContainer;
    
    /**
     * Creates a new instance of HODApplicationViewerRequestBean
     */
    public DRISApplicationViewerRequestBean() {
    }
    
    public boolean isApplicationEndorsed()
    {
        return getSelectedApplication().getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
    }
    
    public boolean isApplicationEligible()
    {
        return getSelectedApplication().getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE);
    }
    
    public String automaticEligiblityApproval()
    {
        try 
        {
            dRISApprovalServiceLocal.checkApplicationForEligiblity(sessionManagerBean.getSession(), getSelectedApplication());
            return navigationManagerBean.goToDRISApprovalServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(DRISApplicationViewerRequestBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    public String manualEligiblityApproval()
    {
        try 
        {
            dRISApprovalServiceLocal.setApplicationEligibleStatus(sessionManagerBean.getSession(), getSelectedApplication(), true);
            return navigationManagerBean.goToDRISApprovalServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(DRISApplicationViewerRequestBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    public String manualEligiblityDecline()
    {
        try 
        {
            dRISApprovalServiceLocal.setApplicationEligibleStatus(sessionManagerBean.getSession(), getSelectedApplication(), false);
            return navigationManagerBean.goToDRISApprovalServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(DRISApplicationViewerRequestBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage(0, Application.class);
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    } 
    
}
