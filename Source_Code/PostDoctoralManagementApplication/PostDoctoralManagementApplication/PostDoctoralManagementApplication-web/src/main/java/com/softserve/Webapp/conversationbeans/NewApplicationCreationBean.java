/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import auto.softserve.XMLEntities.CV.AdditionalInformation;
import auto.softserve.XMLEntities.CV.Item;
import auto.softserve.XMLEntities.CV.OtherContributions;
import auto.softserve.XMLEntities.CV.Reference;
import auto.softserve.XMLEntities.CV.ResearchOutput;
import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.application.Member;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.UserAlreadyExistsException;
import com.softserve.Webapp.depenedentbeans.ApplicationCreationDependBean;
import com.softserve.Webapp.depenedentbeans.CVCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.NewApplicationServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
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
    private CVCreationDependBean cVCreationDependBean;
    @Inject
    private ApplicationCreationDependBean ApplicationCreationDependBean;
    
    @Inject
    private Conversation conversation;
    
    @EJB
    private NewApplicationServiceLocal newApplicationServiceLocal;
    
    private int wizardActiveTab;
    
       
    
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
        try 
        {  
            Session session = null;
            
            session = sessionManagerBean.getSession();
            ApplicationCreationDependBean.init(newApplicationServiceLocal.getOpenApplication(session));
        
            wizardActiveTab = 0;
            cVCreationDependBean.init(session.getUser().getCv());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToApplicationServicesHomeView());
        }
    }
   
    public int getWizardActiveTab() {
        return wizardActiveTab;
    }

    public void setWizardActiveTab(int wizardActiveTab) {
        this.wizardActiveTab = wizardActiveTab;
    }

    public ApplicationCreationDependBean getApplicationCreationDependBean() {
        return ApplicationCreationDependBean;
    }

    public void setApplicationCreationDependBean(ApplicationCreationDependBean ApplicationCreationDependBean) {
        this.ApplicationCreationDependBean = ApplicationCreationDependBean;
    }

    public CVCreationDependBean getCVCreationDependBean() {
        return cVCreationDependBean;
    }

    public void setCVCreationDependBean(CVCreationDependBean cVCreationDependBean) {
        this.cVCreationDependBean = cVCreationDependBean;
    }
    
        
    public void completeCV()
    {
        try
        {
            if(cVCreationDependBean.getAcademicQualificationList().size() < 1)
            {
               throw new Exception("You need to have at least one academic qualification.");
            }
            if(cVCreationDependBean.getExperienceList().size() < 1)
            {
                throw new Exception("You need to have at least one work experience.");
            }
            if(cVCreationDependBean.getOtherContributionsXMLEntity().getItems().size() < 1)
            {
                throw new Exception("You need to have at least one other conttribution.");
            }            
            if(cVCreationDependBean.getResearchOutputXMLEntity().getReferences().size() < 1)
            {
                throw new Exception("You need to have at least one reference.");
            }
            
            Session session = sessionManagerBean.getSession();
            
            Cv cv = cVCreationDependBean.getCombinedCv();
            cv.setCvID(session.getUser().getSystemID());
            cv.setPerson(session.getUser());
            newApplicationServiceLocal.createProspectiveFellowCV(sessionManagerBean.getSession(), cv);
            
            wizardActiveTab = 1;
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }        
    }
    
    public void completeApplication()
    {
        try
        {
            if(ApplicationCreationDependBean.getInformationXMLEntity().getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one project aim.");
            }
            
            if(ApplicationCreationDependBean.getInformationXMLEntity().getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one expected project outcome.");
            }
            
            Session session = sessionManagerBean.getSession();
            
            Application openApplication = ApplicationCreationDependBean.getCombinedApplication();
            openApplication.setFellow(session.getUser());
            if(openApplication.getGrantHolder() != null && openApplication.getGrantHolder().getSystemID() == null)
            {
                openApplication.setGrantHolder(null);
                
            }
            
            newApplicationServiceLocal.createNewApplication(session, openApplication);
            
            if(openApplication.getGrantHolder() == null)
            {                
                ApplicationCreationDependBean.getGrantHolder().setUpEmployee(true);
                wizardActiveTab = 2;
            }
            else
            {            
                if(ApplicationCreationDependBean.getApplication().getFundingType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC))
                {
                    wizardActiveTab = 3;
                }
                else
                {
                    wizardActiveTab = 4;
                }
            }
            sessionManagerBean.clearSessionStorage();
            
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
    }
    
    public void completeGrantHolderSpecification()
    {
        try 
        {            
            newApplicationServiceLocal.linkGrantHolderToApplication(sessionManagerBean.getSession(), ApplicationCreationDependBean.getApplication(), ApplicationCreationDependBean.getGrantHolder());
            
            if(ApplicationCreationDependBean.getApplication().getFundingType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC))
            {
                wizardActiveTab = 3;
            }
            else
            {
                wizardActiveTab = 4;
            }
            
            sessionManagerBean.clearSessionStorage();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
        
    }
    
    public void completeRefereeSpecification()
    {        
        try 
        {
            if(ApplicationCreationDependBean.getReferees().size() < 2)
            {
                throw new Exception("You need at least 2 to referees for this application");
            }
            for(Person referee: ApplicationCreationDependBean.getReferees())
            {
                System.out.println("ref :" + referee.toString());
                newApplicationServiceLocal.linkRefereeToApplication(sessionManagerBean.getSession(), ApplicationCreationDependBean.getApplication(), referee);                
            }   
            wizardActiveTab = 4;
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
    }
    
    public String submitApplication()
    {
        try 
        {
            newApplicationServiceLocal.submitApplication(sessionManagerBean.getSession(), ApplicationCreationDependBean.getApplication());
            return navigationManagerBean.goToApplicationServicesHomeView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        } 
    }
}
