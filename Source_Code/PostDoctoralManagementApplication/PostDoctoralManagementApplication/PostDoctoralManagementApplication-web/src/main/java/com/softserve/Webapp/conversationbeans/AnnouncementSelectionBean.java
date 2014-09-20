/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Announcement;
import com.softserve.Webapp.depenedentbeans.AnnouncementFilterDependBean;
import com.softserve.Webapp.depenedentbeans.AnnouncementViewerDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "announcementSelectionBean")
@ConversationScoped
public class AnnouncementSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @Inject
    private AnnouncementViewerDependBean announcementViewerDependBean;
    @Inject
    private AnnouncementFilterDependBean pendingAnnouncementBean;
    @Inject
    private AnnouncementFilterDependBean activeAnnouncementBean;
    
    /**
     * Creates a new instance of AnnouncementSelectionBean
     */
    public AnnouncementSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        activeAnnouncementBean.init(announcementViewerDependBean.getActiveAnnouncements());
        pendingAnnouncementBean.init(announcementViewerDependBean.getPendingAnnouncements());
        
    }
    
    public AnnouncementFilterDependBean getActiveAnnouncementBean() {
        return activeAnnouncementBean;
    }

    public AnnouncementFilterDependBean getPendingAnnouncementBean() {
        return pendingAnnouncementBean;
    }

    public AnnouncementViewerDependBean getAnnouncementViewerDependBean() {
        return announcementViewerDependBean;
    }
    
    public String editAnnouncement(Announcement announcement)
    {
        sessionManagerBean.addObjectToSessionStorage("ANNOUNCEMENT", announcement);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToAnnouncementServiceAnnouncementEditView();
    }
    
    
}
