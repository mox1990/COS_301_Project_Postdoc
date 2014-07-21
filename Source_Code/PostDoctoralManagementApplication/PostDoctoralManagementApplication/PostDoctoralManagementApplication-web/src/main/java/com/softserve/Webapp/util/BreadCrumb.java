/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.util;

import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class BreadCrumb implements Serializable {
    
    private String PageLinkFromContext = "";
    private String queryString = "";
    private String viewName = "";
    private Conversation conversation = null;

    public BreadCrumb() {
    }
    
    public BreadCrumb(String RootPageLink, String QueryString, String ViewName)
    {
        PageLinkFromContext = RootPageLink;
        queryString = QueryString;
        viewName = ViewName;
        conversation = null;
    }
    
    public BreadCrumb(String RootPageLink, String ViewName)
    {
        PageLinkFromContext = RootPageLink;
        viewName = ViewName;
        queryString = "";
        conversation = null;
    }
    
    public BreadCrumb(String RootPageLink)
    {
        PageLinkFromContext = RootPageLink;
        viewName = "";
        queryString = "";
        conversation = null;
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

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
            
    public void terminateConversation()
    {
        if(conversation != null && !conversation.isTransient())
        {
            conversation.end();
        }
    }
    
    public void stripPath(FacesContext context)
    {
        System.out.print("S0 " + PageLinkFromContext);
        String out = PageLinkFromContext.replace(".xhtml", "");
        System.out.print("S1 " + out);
       
        System.out.print("Striping with " + getServingURL(context) + "/");
        out = out.replace(getServingURL(context) + "/", "");
        System.out.print("S2 " + out);
        PageLinkFromContext = out;
    }
    
    public String getURL(FacesContext context)
    {        
        return getServingURL(context) + "/" + PageLinkFromContext + "?" + queryString;
    }
    
    public String getServingURL(FacesContext context)
    {
        String out = context.getCurrentInstance().getExternalContext().getRequestContextPath()+ context.getCurrentInstance().getExternalContext().getRequestServletPath();
        System.out.print("Serving URL: " + out);
        return out;
    }
    
    public String getURL()
    {
        return PageLinkFromContext + "?" + queryString;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj.getClass().equals(this.getClass()))
        {
            return ((BreadCrumb) obj).getPageLinkFromContext().equals(this.getPageLinkFromContext());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.viewName);
        return hash;
    }
    
    
            
}
