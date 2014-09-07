/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.Webapp.depenedentbeans.ApplicationCreationDependBean;
import com.softserve.Webapp.depenedentbeans.ApplicationReviewRequestCreationDependBean;
import com.softserve.Webapp.depenedentbeans.CVCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.GrantHolderFinalisationServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
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
@Named(value = "grantHolderFinalisationWizardBean")
@ConversationScoped
public class GrantHolderFinalisationWizardBean implements Serializable {
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    @Inject
    private CVCreationDependBean cVCreationDependBean;
    @Inject
    private ApplicationCreationDependBean applicationCreationDependBean;
    @Inject
    private ApplicationReviewRequestCreationDependBean applicationReviewRequestCreationDependBean;
    
    @EJB
    private GrantHolderFinalisationServiceLocal grantHolderFinalisationServiceLocal;
    
    private UIComponent errorContainer;
    
    private int wizardActiveTab;
            
    /**
     * Creates a new instance of GrantHolderFinalisationWizardBean
     */
    public GrantHolderFinalisationWizardBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        Session session = null;       
        
        try 
        {        
            session = sessionManagerBean.getSession();        
            Application application = sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
            if(application == null)
            {
                throw new Exception("No application selected");
            }
            
            applicationCreationDependBean.init(application);
            wizardActiveTab = 0;
            cVCreationDependBean.init(session.getUser().getCv());
        
            applicationReviewRequestCreationDependBean.init(grantHolderFinalisationServiceLocal.getHODsOfApplication(session, applicationCreationDependBean.getApplication()));
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);           
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToGrantHolderFinalisationServiceApplicationSelectionView());
        }
        
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }

    public ApplicationCreationDependBean getApplicationCreationDependBean() {
        return applicationCreationDependBean;
    }

    public void setApplicationCreationDependBean(ApplicationCreationDependBean applicationCreationDependBean) {
        this.applicationCreationDependBean = applicationCreationDependBean;
    }

    public CVCreationDependBean getCVCreationDependBean() {
        return cVCreationDependBean;
    }

    public void setCVCreationDependBean(CVCreationDependBean cVCreationDependBean) {
        this.cVCreationDependBean = cVCreationDependBean;
    }

    public ApplicationReviewRequestCreationDependBean getApplicationReviewRequestCreationDependBean() {
        return applicationReviewRequestCreationDependBean;
    }

    public void setApplicationReviewRequestCreationDependBean(ApplicationReviewRequestCreationDependBean applicationReviewRequestCreationDependBean) {
        this.applicationReviewRequestCreationDependBean = applicationReviewRequestCreationDependBean;
    }
    
    public int getWizardActiveTab() {
        return wizardActiveTab;
    }

    public void setWizardActiveTab(int wizardActiveTab) {
        this.wizardActiveTab = wizardActiveTab;
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
            grantHolderFinalisationServiceLocal.createGrantHolderCV(sessionManagerBean.getSession(), cv);
            
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
            if(applicationCreationDependBean.getInformationXMLEntity().getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one project aim.");
            }
            
            if(applicationCreationDependBean.getInformationXMLEntity().getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one expected project outcome.");
            }
            
            Session session = sessionManagerBean.getSession();
            
            Application openApplication = applicationCreationDependBean.getCombinedApplication();
            
            grantHolderFinalisationServiceLocal.saveChangesToApplication(session, openApplication);

            wizardActiveTab = 2;
            //sessionManagerBean.clearSessionStorage();
            
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
        
    }
    
    public String finaliseApplication()
    {
        try 
        {            
            Session session = sessionManagerBean.getSession();
            if(applicationReviewRequestCreationDependBean.getPerson() != null && (applicationReviewRequestCreationDependBean.getManualSpecification() || applicationReviewRequestCreationDependBean.getPerson().getSystemID() != null))
            {
                grantHolderFinalisationServiceLocal.requestSpecificHODtoReview(session, applicationCreationDependBean.getApplication(), applicationReviewRequestCreationDependBean.getPerson());
                grantHolderFinalisationServiceLocal.finaliseApplication(session, applicationCreationDependBean.getCombinedApplication());
            }
            else
            {
                throw new Exception("An HOD must be specified");
            }
            
            return navigationManagerBean.goToGrantHolderFinalisationServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            applicationReviewRequestCreationDependBean.setPerson(new Person());
            return "";
        }
        
        
    }
}
