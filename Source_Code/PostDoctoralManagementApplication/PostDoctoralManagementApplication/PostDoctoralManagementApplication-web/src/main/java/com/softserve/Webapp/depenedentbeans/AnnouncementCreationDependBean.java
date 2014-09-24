/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.Announcement;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultUploadedFile;
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
        System.out.println("==============Combined announcment " + fileUpload);
        Announcement a = new Announcement();
        
        if(fileUpload != null)
        {        
            System.out.println("==============Using file " + fileUpload.getFileName() + " " + fileUpload.getContentType());
            
            System.out.println("==============file content " + Arrays.toString(fileUpload.getContents()));
            if(fileUpload.getContents() == null)
            {
                try 
                {
                    a.setImage(IOUtil.toByteArray(fileUpload.getInputstream()));
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(AnnouncementCreationDependBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                a.setImage(fileUpload.getContents());
            }
            
            
            
        }
        
        if(a.getImage() == null)
        {
            a.setImage(announcement.getImage());
        }
        
        System.out.println("==============file content in announcement" + Arrays.toString(a.getImage()));
        
        a.setAnnouncementID(announcement.getAnnouncementID());
        a.setTimestamp(announcement.getTimestamp());
        a.setEndDate(announcement.getEndDate());
        a.setHeadline(announcement.getHeadline());
        a.setStartDate(announcement.getStartDate());
        a.setMessage(announcement.getMessage());
        
        System.out.println("==============Announcement " + a.getHeadline()+ " " + a.getMessage() + " " + a);
        
        return a;
    }
}
