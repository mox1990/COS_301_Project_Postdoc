/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.depenedentbeans.AnnouncementCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.AnnouncementManagementServiceLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "announcementCreationBean")
@ConversationScoped
public class AnnouncementCreationBean implements Serializable {
    
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
     * Creates a new instance of AnnouncementCreationBean
     */
    public AnnouncementCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        announcementCreationDependBean.init(null);
    }

    public AnnouncementCreationDependBean getAnnouncementCreationDependBean() {
        return announcementCreationDependBean;
    }

    public void setAnnouncementCreationDependBean(AnnouncementCreationDependBean announcementCreationDependBean) {
        this.announcementCreationDependBean = announcementCreationDependBean;
    }
    
    public void uploadEvent(FileUploadEvent event)
    {
        if(event.getFile() != null)
        {
            announcementCreationDependBean.setFileUpload(event.getFile());
            MessageUtil.CreateGlobalFacesMessage("Uploaded", announcementCreationDependBean.getFileUpload().toString(),FacesMessage.SEVERITY_INFO);
        }
    }
    
    public String preformCreationAnnouncmentRequest()
    {
        try
        {
            announcementManagementServiceLocal.createAnnouncement(sessionManagerBean.getSession(), announcementCreationDependBean.getCombinedAnnouncement());
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
