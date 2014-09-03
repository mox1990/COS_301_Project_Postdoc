/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author kgothatso
 */
@Named(value = "dataImportsAndExports")
@RequestScoped
public class DataImportsAndExports {

    /**
     * Creates a new instance of DataImportsAndExports
     */
    public DataImportsAndExports() {
    }
    
    public void importApplications(FileUploadEvent event) 
    { 
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded."); 
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file       
        try 
        {
            event.getFile().getInputstream();
            // dataImportsAndExports.importApplications(event.getFile().getInputstream());
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void importPersons(FileUploadEvent event) 
    { 
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded."); 
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file       
        try 
        {
            event.getFile().getInputstream();
            // dataImportsAndExports.importPersons(event.getFile().getInputstream());
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}
