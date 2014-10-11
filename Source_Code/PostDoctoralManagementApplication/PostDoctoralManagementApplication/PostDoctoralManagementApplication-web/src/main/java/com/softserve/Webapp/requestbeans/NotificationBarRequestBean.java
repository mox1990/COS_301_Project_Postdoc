/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.persistence.DBEntities.Announcement;
import com.softserve.Webapp.depenedentbeans.AnnouncementViewerDependBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.requestresponseclasses.Issue;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotifierServicesLocal;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "notificationBarRequestBean")
@RequestScoped
public class NotificationBarRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    
    @Inject
    private AnnouncementViewerDependBean announcementViewerDependBean;
    
    @EJB
    private NotifierServicesLocal notifierServicesLocal;
    
    
    /**
     * Creates a new instance of NotificationBarRequestBean
     */
    public NotificationBarRequestBean() {
    }
    
    public boolean isUserLoggedIn()
    {
        try
        {               
            return sessionManagerBean.getSession().getLoggedInStatus();
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    public String getUserGreeting()
    {
        try
        { 
            Session session = sessionManagerBean.getSession();
            if(session.getLoggedInStatus())
            {
                return "You are logged in as " + session.getUser().getCompleteName() + " (" + session.getUser().getSystemID() + ")";
            }
            else
            {
                return "Please login to view details";
            }
        }
        catch(Exception ex)
        {
            return "Please login to view details";
        }
    }
    
    public List<Issue> getOutstandingIssues()
    {
        System.out.println("====================================================================================================Start Notification bar init");
        try 
        {
            List<Issue> issues = notifierServicesLocal.loadAllPendingIssuesForSession(sessionManagerBean.getSession());
            System.out.println("====================================================================================================End Notification bar init");
            return issues;
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(NotificationBarRequestBean.class, ex);
            System.out.println("====================================================================================================End Notification bar init");
            return new ArrayList<Issue>();
        }
        
    }

    public AnnouncementViewerDependBean getAnnouncementViewerDependBean() {
        return announcementViewerDependBean;
    }
       
    
}
