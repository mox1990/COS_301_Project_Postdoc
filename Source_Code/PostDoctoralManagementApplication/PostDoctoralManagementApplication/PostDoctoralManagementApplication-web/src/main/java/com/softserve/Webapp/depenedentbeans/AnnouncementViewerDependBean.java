/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Announcement;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementServiceLocal;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "announcementViewerDependBean")
@Dependent
public class AnnouncementViewerDependBean implements Serializable {
    
    @EJB
    private AnnouncementManagementServiceLocal announcementManagementServiceLocal;
    
    /**
     * Creates a new instance of AnnouncementViewerDependBean
     */
    public AnnouncementViewerDependBean() {
    }
    
    public boolean isImageEmpty(Announcement announcement)
    {        
        return announcement == null || announcement.getImage() == null || announcement.getImage().length == 0;
    }
    
    public StreamedContent getImageForAnnoucement()
    {
        
        
        if(FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)
        {
            return new DefaultStreamedContent();
        }
        else
        {
            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("announceID"));
            List<Announcement> announcements = getActiveAnnouncements();
            int index = announcements.indexOf(new Announcement(id));

            Announcement announcement = announcements.get(index);
            if(isImageEmpty(announcement))
            {
                return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]));
            }
            else
            {
                return new DefaultStreamedContent(new ByteArrayInputStream(announcement.getImage()));
            }
        }
    }
    
    public List<Announcement> getActiveAnnouncements()
    {
        try 
        {
            return announcementManagementServiceLocal.loadAllActiveAnnouncements();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(AnnouncementViewerDependBean.class, ex);
            return new ArrayList<Announcement>();
        }
    }
    
    public List<Announcement> getPendingAnnouncements()
    {
        try 
        {
            return announcementManagementServiceLocal.loadAllPendingAnnouncements();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(AnnouncementViewerDependBean.class, ex);
            return new ArrayList<Announcement>();
        }
    }
}
