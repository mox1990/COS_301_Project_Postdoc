/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import auto.softserve.XMLEntities.application.*;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hodServicesBean")
@ConversationScoped
public class HODServicesBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private Conversation conversation;
    @EJB
    private HODApprovalServicesLocal hODApprovalServicesLocal;
    
    /*private List<Application> pendingApplications;
    
    private int startIndexOfPendingApplications = 0;
    private final int maxNoOfPendingApplications = 20;*/
    private Application currentlySelectedApplication = null;
    private UIComponent errorContainer;
    
    /**
     * Creates a new instance of HODRecommendationServiceBean
     */
    public HODServicesBean() {
    }

    public Application getCurrentlySelectedApplication() {
        return currentlySelectedApplication;
    }

    public void setCurrentlySelectedApplication(Application currentlySelectedApplication) {
        this.currentlySelectedApplication = currentlySelectedApplication;
    }    
    
    public List<Application> loadPendingApplications()
    {
        conversation.begin();
        try 
        {
            return hODApprovalServicesLocal.loadPendingApplications(sessionManagerBean.getSession(),0,hODApprovalServicesLocal.countTotalPendingApplications(sessionManagerBean.getSession()));
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return new ArrayList<Application>();
        }
    }
    
    public String goToDeclineView()
    {
        return "HODRecommendationService_ApplicationDecline?faces-redirect=true";
    }
    
    public String declineCurrentlySelectedApplication(String reasonMessage)
    {
        try 
        {
            hODApprovalServicesLocal.denyAppliction(sessionManagerBean.getSession(), currentlySelectedApplication, reasonMessage);
            conversation.end();
            return "HODRecommendationService_ApplicationSelection?faces-redirect=true";
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex); 
            return "";
        }        
    }
    
    public String goToAmmendView()
    {
        return "HODRecommendationService_ApplicationAmmend?faces-redirect=true";
    }
    
    public String ammendCurrentlySelectedApplication(String reasonMessage)
    {
        try 
        {
            hODApprovalServicesLocal.ammendAppliction(sessionManagerBean.getSession(), currentlySelectedApplication, reasonMessage);
            conversation.end();
            return "HODRecommendationService_ApplicationSelection?faces-redirect=true";
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex); 
            return "";
        }        
    }
    
    public String goToRecommendView()
    {
        return "HODRecommendationService_ApplicationRecommend?faces-redirect=true";
    }
    
    public String recommendCurrentlySelectedApplication(RecommendationReport report)
    {
        try 
        {
            hODApprovalServicesLocal.approveApplication(sessionManagerBean.getSession(), currentlySelectedApplication, report);
            conversation.end();
            return "HODRecommendationService_ApplicationSelection?faces-redirect=true";
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(errorContainer, ex); 
            return "";
        }        
    }
    
    public String goToApplicationSelectionView()
    {
        conversation.end();
        return "HODRecommendationService_ApplicationSelection?faces-redirect=true";
    }
    
    /* Old pagenation attempt
    public void setStartIndexOfPendingApplications(int startIndexOfPendingApplications) {
        this.startIndexOfPendingApplications = startIndexOfPendingApplications;
    }    

    public List<Application> getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(List<Application> pendingApplications) {
        this.pendingApplications = pendingApplications;
    }    
    
    public void addNextApplicationsToList()
    {
        try 
        {
            if(startIndexOfPendingApplications + maxNoOfPendingApplications < hODApprovalServicesLocal.countTotalPendingApplications(sessionManagerBean.getSession()))
            {
                startIndexOfPendingApplications += maxNoOfPendingApplications;
                
                pendingApplications.addAll(loadPendingApplications());
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(HODServicesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
