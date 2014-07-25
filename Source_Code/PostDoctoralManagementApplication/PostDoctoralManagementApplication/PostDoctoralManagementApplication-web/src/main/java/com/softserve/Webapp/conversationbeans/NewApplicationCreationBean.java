/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.ejb.NewApplicationServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "newApplicationCreationBean")
@ConversationScoped
public class NewApplicationCreationBean implements Serializable {

    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private NewApplicationServiceLocal newApplicationServiceLocal;
    
    private UIComponent errorContainer;
    
    private Cv propectiveFellowCv;
    private Application application;
    private Person grantHolder;
    private List<Person> referees;
    private Person currentReferee;
    
    /**
     * Creates a new instance of NewApplicationCreationBean
     */
    public NewApplicationCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        Person fellow;
        try 
        {
            fellow = sessionManagerBean.getSession().getUser();
            
            if(newApplicationServiceLocal.canFellowOpenANewApplication(fellow))
            {
        
                referees = new ArrayList<Person>();
                grantHolder = new Person();
                currentReferee = new Person();
                if(fellow.getCv() != null)
                {
                    propectiveFellowCv = fellow.getCv();
                }
                else
                {
                    propectiveFellowCv = new Cv();
                }
                application = new Application();
            }
            else
            {
                application = newApplicationServiceLocal.getOpenApplication(sessionManagerBean.getSession());
                if(application != null)
                {
                    if(fellow.getCv() != null)
                    {
                        propectiveFellowCv = fellow.getCv();
                    }
                    else
                    {
                        propectiveFellowCv = new Cv();
                    }
                    
                    referees = new ArrayList<Person>();
                    grantHolder = new Person();
                    currentReferee = new Person();                    
                }
                else
                {
                    
                }
            }
        }
        catch(Exception ex)
        {
            
        }
    }
}
