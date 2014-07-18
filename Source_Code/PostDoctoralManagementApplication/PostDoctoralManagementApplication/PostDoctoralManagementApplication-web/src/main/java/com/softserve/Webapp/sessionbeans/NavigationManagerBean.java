/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import com.softserve.Webapp.util.BreadCrumb;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "navigationManagerBean")
@SessionScoped
public class NavigationManagerBean implements Serializable {
    
    private ArrayList<BreadCrumb> breadCrumbs;
    
    /**
     * Creates a new instance of navigationBean
     */
    public NavigationManagerBean() 
    {
        breadCrumbs = new ArrayList<BreadCrumb>();
    }
    
    @PostConstruct
    public void init()
    {
        
    }
    
    public List<BreadCrumb> getBreadCrumbs() {
        return breadCrumbs;
    }
    
    private void addToBreadCrumbs(BreadCrumb breadCrumb)
    {
        if(!breadCrumbs.contains(breadCrumb))
        {
            breadCrumbs.add(breadCrumb);
        }
    }
    
    private String goToLink(BreadCrumb breadCrumb)
    {
        if(breadCrumbs.contains(breadCrumb) && !getLatestBreadCrumb().equals(breadCrumb))
        {
            return goToBreadCrumb(breadCrumbs.indexOf(breadCrumb));
        }
        else
        {
            breadCrumb.setQueryString("faces-redirect=true");
            
            return breadCrumb.getURL(FacesContext.getCurrentInstance());
        }
    }
    
    public BreadCrumb getLatestBreadCrumb()
    {
        return breadCrumbs.get(breadCrumbs.size() - 1);
    }
    
    public String CheckPath(String path)
    {
        int i = getIndexOfBreadCrumbWithPath(path);
        
        if(i < 0)
        {
            return goToLink(getLatestBreadCrumb());
        }
        
        return "";
    }
    
    public void applicationBasedNavigation(String path)
    {
        if(!path.equals(""))
        {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, path);
        }
    }
    
    public void clearBreadCrumbsTo(int index)
    {
        while(breadCrumbs.size() > 0 && breadCrumbs.size() - 1 > index)
        {
            breadCrumbs.get(breadCrumbs.size() - 1).terminateConversation();
            breadCrumbs.remove(breadCrumbs.size() - 1);
        }
    }
    
    public String goToBreadCrumb(int index)
    {
        BreadCrumb breadCrumb = breadCrumbs.get(index);
        clearBreadCrumbsTo(index);
        
        return goToLink(breadCrumb);
    }
    
    public String goToPreviousBreadCrumb()
    {
        return goToBreadCrumb(breadCrumbs.size() - 2);
    }
    
    public int getIndexOfBreadCrumbWithPath(String path)
    {
        BreadCrumb breadCrumb = new BreadCrumb(path);
        
        return breadCrumbs.indexOf(breadCrumb);
    }
    
    public String goToPortalView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("index", "Portal");
        
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountCreationForProspectiveFellowView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagement_ProspectiveFellowCreateUser", "Prospective Fellow account creation view");
        
        return goToLink(breadCrumb);
    }
    
    public String goToWelcomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("welcome", "Home");
        breadCrumbs.clear();
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationServices_Home", "Application services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToReportServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ReportServices_Home", "Report services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDataImportAndExportServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DataImportAndExportServices_Home", "Data import and export services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_Home", "User account management services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToArchivalServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ArchivalServices_Home", "Archival services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToAduitTrailServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("AuditTrailServices_Home", "Audit trail services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODApplicationViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationViewer", "Application viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODDeclineView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationDecline", "Decline application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODAmmendView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationAmmend", "Request application ammendment");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODRecommendView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationRecommend", "Recommend application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationSelection", "Application selection view");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    
    
}
