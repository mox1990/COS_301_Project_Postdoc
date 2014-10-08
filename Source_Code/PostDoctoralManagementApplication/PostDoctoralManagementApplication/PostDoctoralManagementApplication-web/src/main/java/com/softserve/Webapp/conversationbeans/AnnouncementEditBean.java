/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.persistence.DBEntities.Announcement;
import com.softserve.Webapp.depenedentbeans.AnnouncementCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementServiceLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "announcementEditBean")
@ConversationScoped
public class AnnouncementEditBean implements Serializable {

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @Inject
    private AnnouncementCreationDependBean announcementCreationDependBean;
    
    @EJB
    private AnnouncementManagementServiceLocal announcementManagementServiceLocal;
    
    /**
     * Creates a new instance of AnnouncementEditBean
     */
    public AnnouncementEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        try
        {
            conversationManagerBean.registerConversation(conversation);
            conversationManagerBean.startConversation(conversation);

            Announcement announcement = sessionManagerBean.getObjectFromSessionStorage("ANNOUNCEMENT", Announcement.class);

            if(announcement == null)
            {
                throw new Exception("No announcement selected");
            }

            announcementCreationDependBean.init(announcement);
            
            sessionManagerBean.addObjectToSessionStorage("FILEUPLOAD", null);
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(AnnouncementCreationBean.class, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToAnnouncementServiceAnnouncementSelectionView());
        }
    }

    public AnnouncementCreationDependBean getAnnouncementCreationDependBean() {
        return announcementCreationDependBean;
    }
    
    public String preformUpdateAnnouncmentRequest()
    {
        try
        {
            UploadedFile uploadedFile = sessionManagerBean.getObjectFromSessionStorage("FILEUPLOAD", UploadedFile.class);
            if(uploadedFile != null)
            {
                announcementCreationDependBean.setFileUpload(uploadedFile);
            }
            System.out.println("===Announce " + announcementCreationDependBean.getAnnouncement().getAnnouncementID());
            announcementManagementServiceLocal.updateAnnouncement(sessionManagerBean.getSession(), announcementCreationDependBean.getCombinedAnnouncement());
            return navigationManagerBean.goToAnnouncementServiceHomeView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(AnnouncementCreationBean.class, ex);
            return "";
        }
    }
}
