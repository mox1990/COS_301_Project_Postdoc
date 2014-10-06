/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.ArchivalServiceLocal;
import javax.faces.component.UIComponent;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@Named(value = "archivalBean")
@RequestScoped
public class ArchivalBean {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ArchivalServiceLocal archivalServiceLocal;
    private UIComponent errorContainer;
    
    public void restoreDB()
    {
        try
        {
            archivalServiceLocal.restoreBackupToWorkingDatabase(sessionManagerBean.getSession());
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
        }
    }
    
    public void backupDB()
    {
        
        try
        {
            archivalServiceLocal.backupDatabase(sessionManagerBean.getSession());
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
        }
    }
    
    public void retrieveArchivedInformation(String file)
    {
        try
        {
            archivalServiceLocal.retrieveArchievedInformation(sessionManagerBean.getSession());
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
        }
    }
}
