/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.util;

import javax.faces.context.FacesContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NavigationLink {
    
    private String PageLinkFromContext = "";
    private String queryString = "";
    private String viewName = "";

    public NavigationLink() {
    }
    
    public NavigationLink(String RootPageLink, String QueryString, String ViewName)
    {
        PageLinkFromContext = RootPageLink;
        queryString = QueryString;
        viewName = ViewName;
    }
    
    public NavigationLink(String RootPageLink, String ViewName)
    {
        PageLinkFromContext = RootPageLink;
        viewName = ViewName;
        queryString = "";
    }
    
    public NavigationLink(String RootPageLink)
    {
        PageLinkFromContext = RootPageLink;
        viewName = "";
        queryString = "";
    }

    public String getPageLinkFromContext() {
        return PageLinkFromContext;
    }

    public void setPageLinkFromContext(String PageLinkFromContext) {
        this.PageLinkFromContext = PageLinkFromContext;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    
    public String getURL(FacesContext context)
    {
        return context.getCurrentInstance().getExternalContext().getContextName() + PageLinkFromContext + "?" + queryString;
    }
            
}
