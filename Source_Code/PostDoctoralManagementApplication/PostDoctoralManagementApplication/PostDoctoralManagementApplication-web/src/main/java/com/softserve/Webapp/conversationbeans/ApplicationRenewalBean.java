/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationRenewalServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationRenewalBean")
@ConversationScoped
public class ApplicationRenewalBean implements Serializable {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject 
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation convesation;       
    
    @EJB
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    
    private Application oldApplication;
    private Application newApplication;
    
    
    
    /**
     * Creates a new instance of ApplicationRenewalBean
     */
    public ApplicationRenewalBean() {
    }
    
    @PostConstruct
    public void init()
    {
        newApplication = new Application();
        oldApplication = sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }
    
    
    public String preformApplicationRenewalRequest()
    {
        return "";
    }
    
    
}
