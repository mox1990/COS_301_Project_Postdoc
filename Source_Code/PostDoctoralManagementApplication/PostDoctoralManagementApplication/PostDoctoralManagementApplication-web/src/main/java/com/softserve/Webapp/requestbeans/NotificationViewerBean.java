/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.Notification;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.NotificationServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "notificationViewerRequestBean")
@ConversationScoped
public class NotificationViewerBean implements Serializable {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject 
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation convesation;       
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    
    private List<Notification> notifications;
    private List<Notification> filteredNotifications;
    
    /**
     * Creates a new instance of NotificationViewerRequestBean
     */
    public NotificationViewerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        try
        {
            conversationManagerBean.registerConversation(convesation);
            conversationManagerBean.startConversation(convesation);
            
            Session session = sessionManagerBean.getSession();
            notifications = notificationServiceLocal.getAllNotificationsForPerson(session, session.getUser());
            filteredNotifications = new ArrayList<Notification>();            
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NotificationViewerBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToWelcomeView());
        }        
    }
    
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getFilteredNotifications() {
        return filteredNotifications;
    }

    public void setFilteredNotifications(List<Notification> filteredNotifications) {
        this.filteredNotifications = filteredNotifications;
    }
    
        
}
