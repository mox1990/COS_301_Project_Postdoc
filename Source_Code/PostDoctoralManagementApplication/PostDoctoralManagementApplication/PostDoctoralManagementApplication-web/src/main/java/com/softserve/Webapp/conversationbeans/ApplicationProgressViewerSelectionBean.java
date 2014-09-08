/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Webapp.depenedentbeans.ApplicationFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ApplicationProgressViewerServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value="applicationProgressViewerSelectionBean")
@ConversationScoped
public class ApplicationProgressViewerSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBeanFellow;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBeanGrantHolder;
    @Inject
    private ApplicationFilterDependBean applicationFilterDependBeanAll;
    @Inject
    private Conversation conversation;
    
    @EJB
    private ApplicationProgressViewerServiceLocal applicationProgressViewerServiceLocal;
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            
            Session session = sessionManagerBean.getSession();
            if(isFellowApplicationDisplayable())
            {
                applicationFilterDependBeanFellow.init(session.getUser().getApplicationList1());
            }
            if(isGrantHolderApplicationDisplayable())
            {
                applicationFilterDependBeanGrantHolder.init(session.getUser().getApplicationList2());
            }
            if(isAllApplicationDisplayable())
            {
                applicationFilterDependBeanAll.init(applicationProgressViewerServiceLocal.getAllApplications(session));
            }
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToApplicationServicesHomeView());
        }
    }

    public ApplicationFilterDependBean getApplicationFilterDependBeanAll() {
        return applicationFilterDependBeanAll;
    }

    public ApplicationFilterDependBean getApplicationFilterDependBeanFellow() {
        return applicationFilterDependBeanFellow;
    }

    public ApplicationFilterDependBean getApplicationFilterDependBeanGrantHolder() {
        return applicationFilterDependBeanGrantHolder;
    }
        
    public String viewApplication(Application application)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("APPLICATION",application);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToApplicationProgressViewerApplicationProgressView();
    }
    
    public boolean isFellowApplicationDisplayable()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
            
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
    public boolean isGrantHolderApplicationDisplayable()
    {
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveSecurityRole(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
    public boolean isAllApplicationDisplayable()
    {
        try 
        {
            ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
            securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ApplicationProgressViewerSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);   
            return false;
        }
    }
    
}
