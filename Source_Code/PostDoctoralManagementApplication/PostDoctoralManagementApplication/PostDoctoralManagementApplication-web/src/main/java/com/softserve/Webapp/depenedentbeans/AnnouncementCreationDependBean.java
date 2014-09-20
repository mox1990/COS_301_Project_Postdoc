/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Announcement;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "announcementCreationDependBean")
@Dependent
public class AnnouncementCreationDependBean implements Serializable{

    private Announcement announcement;
    private UploadedFile fileUpload;
    
    /**
     * Creates a new instance of AnnouncementCreationDependBean
     */
    public AnnouncementCreationDependBean() {
    }
    
    public void init(Announcement announcement)
    {
        if(announcement == null)
        {
            this.announcement = new Announcement();
        }
        else
        {
            this.announcement = announcement;
        }        
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public UploadedFile getFileUpload() {
        return fileUpload;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public void setFileUpload(UploadedFile fileUpload) {
        this.fileUpload = fileUpload;
    }
    
    public Announcement getCombinedAnnouncement()
    {
        announcement.setImage(fileUpload.getContents());
        return announcement;
    }
}
