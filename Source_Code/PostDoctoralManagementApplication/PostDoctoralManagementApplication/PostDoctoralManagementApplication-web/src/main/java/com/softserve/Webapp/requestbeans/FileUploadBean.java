/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.depenedentbeans.AnnouncementCreationDependBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.MessageUtil;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "fileUploadBean")
@RequestScoped
public class FileUploadBean {

    @Inject
    private SessionManagerBean sessionManagerBean;
    
    /**
     * Creates a new instance of FileUploadBean
     */
    public FileUploadBean() {
    }
    
    public void uploadEvent(FileUploadEvent event)
    {
        if(event.getFile() != null)
        {            
            System.out.println("=========Uploading file handler: file processing");
            sessionManagerBean.addObjectToSessionStorage("FILEUPLOAD", event.getFile());
            System.out.println("=========Uploading file handler: " + event.getFile().getFileName());
            System.out.println("=========Uploading file handler: " + event.getFile().getContents());
            
            MessageUtil.CreateGlobalFacesMessage("Uploaded file", event.getFile().getFileName(),FacesMessage.SEVERITY_INFO);
        }
    }
}
