/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import com.softserve.Webapp.util.NavigationLink;
import java.io.Serializable;
import java.util.ArrayList;
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

    private ArrayList<NavigationLink> breadCrumbs;
    
    /**
     * Creates a new instance of navigationBean
     */
    public NavigationManagerBean() {
    }
    
    private String goToLink(NavigationLink link)
    {
        link.setQueryString("force-redirect=true");
        return link.getURL(FacesContext.getCurrentInstance());
    }
    
    public String goToBreadCrumb(int index)
    {
        NavigationLink link = breadCrumbs.get(index);
        while(breadCrumbs.size() - 1 > index)
        {
            breadCrumbs.remove(breadCrumbs.size() - 1);
        }
        
        return goToLink(link);
    }
    
    public String goToPreviousBreadCrumb()
    {
        return goToBreadCrumb(breadCrumbs.size() - 2);
    }

    public ArrayList<NavigationLink> getBreadCrumbs() {
        return breadCrumbs;
    }
    
    public String goToHODApplicationViewer()
    {
        NavigationLink link = new NavigationLink("HODRecommendationService_ApplicationViewer", "Application viewer");
        breadCrumbs.add(link);
        
        return goToLink(link);
    }
    
    public String goToHODDeclineView()
    {        
        NavigationLink link = new NavigationLink("HODRecommendationService_ApplicationDecline", "Decline application");
        breadCrumbs.add(link);
        
        return goToLink(link);
    }
    
    public String goToHODAmmendView()
    {
        NavigationLink link = new NavigationLink("HODRecommendationService_ApplicationAmmend", "Request application ammendment");
        breadCrumbs.add(link);
        
        return goToLink(link);
    }
    
    public String goToHODRecommendView()
    {        
        NavigationLink link = new NavigationLink("HODRecommendationService_ApplicationRecommend", "Recommend application");
        breadCrumbs.add(link);
        
        return goToLink(link);
    }
    
    public String goToHODApplicationSelectionView()
    {
        NavigationLink link = new NavigationLink("HODRecommendationService_ApplicationSelection", "Application selection view");
        breadCrumbs.add(link);
        
        return goToLink(link);
    }
    
}
