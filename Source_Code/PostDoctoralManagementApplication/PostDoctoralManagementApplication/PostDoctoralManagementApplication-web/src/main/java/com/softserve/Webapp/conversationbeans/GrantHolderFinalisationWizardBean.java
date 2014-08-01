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
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.GrantHolderFinalisationServiceLocal;
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
    
    @EJB
    private GrantHolderFinalisationServiceLocal grantHolderFinalisationServiceLocal;
    
    private UIComponent errorContainer;
    
    private int wizardActiveTab;
    
    private Application openApplication;
    private Cv cv;
    
    private List<AcademicQualification> academicQualificationList;
    private AcademicQualification currentQualification;
    
    private List<Experience> experienceList;
    private Experience currentExperience;
    
    private ResearchOutput researchOutputXMLEntity;
    private Reference currentReference;
    
    private OtherContributions otherContributionsXMLEntity;
    private Item currentItem;
    
    private AdditionalInformation additionalInformationXMLEntity;
    
    private ApplicationInformation informationXMLEntity;
    private Member currentMember;
    private String currentAim;
    private String currentExpectedOutcome;
    
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
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToPortalView());
            return;
        }
        
        openApplication = sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
        
        cv = session.getUser().getCv();
        
        if(cv == null)
        {
            cv = new Cv();

            additionalInformationXMLEntity = new AdditionalInformation();
            otherContributionsXMLEntity = new OtherContributions();
            researchOutputXMLEntity = new ResearchOutput();


            academicQualificationList = new ArrayList<AcademicQualification>();
            experienceList = new ArrayList<Experience>();
        }
        else
        {
            additionalInformationXMLEntity = cv.getAdditionalInformationXMLEntity();
            otherContributionsXMLEntity = cv.getOtherContributionsXMLEntity();
            researchOutputXMLEntity = cv.getResearchOutputXMLEntity();
            
            academicQualificationList = cv.getAcademicQualificationList();
            experienceList = cv.getExperienceList();
        }
        
        currentQualification = new AcademicQualification();
        currentExperience = new Experience();
        currentItem = new Item();
        currentReference = new Reference();  
        
        informationXMLEntity = openApplication.getInformationXMLEntity();
        
        if(informationXMLEntity.getTeamMembers() == null)
        {
            informationXMLEntity.setTeamMembers(new ApplicationInformation.TeamMembers());
        }
        
        if(informationXMLEntity.getProjectAims()== null)
        {
            informationXMLEntity.setProjectAims(new ApplicationInformation.ProjectAims());
        }
        
        if(informationXMLEntity.getExpectedOutcomes()== null)
        {
            informationXMLEntity.setExpectedOutcomes(new ApplicationInformation.ExpectedOutcomes());
        }
        
        
        currentMember = new Member();
        currentAim = "";
        currentExpectedOutcome = "";
    }

    public List<AcademicQualification> getAcademicQualificationList() {
        return academicQualificationList;
    }

    public void setAcademicQualificationList(List<AcademicQualification> academicQualificationList) {
        this.academicQualificationList = academicQualificationList;
    }

    public AdditionalInformation getAdditionalInformationXMLEntity() {
        return additionalInformationXMLEntity;
    }

    public void setAdditionalInformationXMLEntity(AdditionalInformation additionalInformationXMLEntity) {
        this.additionalInformationXMLEntity = additionalInformationXMLEntity;
    }

    public String getCurrentAim() {
        return currentAim;
    }

    public void setCurrentAim(String currentAim) {
        this.currentAim = currentAim;
    }

    public String getCurrentExpectedOutcome() {
        return currentExpectedOutcome;
    }

    public void setCurrentExpectedOutcome(String currentExpectedOutcome) {
        this.currentExpectedOutcome = currentExpectedOutcome;
    }

    public Experience getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(Experience currentExperience) {
        this.currentExperience = currentExperience;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    public void setCurrentMember(Member currentMember) {
        this.currentMember = currentMember;
    }

    public AcademicQualification getCurrentQualification() {
        return currentQualification;
    }

    public void setCurrentQualification(AcademicQualification currentQualification) {
        this.currentQualification = currentQualification;
    }

    public Reference getCurrentReference() {
        return currentReference;
    }

    public void setCurrentReference(Reference currentReference) {
        this.currentReference = currentReference;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public ApplicationInformation getInformationXMLEntity() {
        return informationXMLEntity;
    }

    public void setInformationXMLEntity(ApplicationInformation informationXMLEntity) {
        this.informationXMLEntity = informationXMLEntity;
    }

    public Application getOpenApplication() {
        return openApplication;
    }

    public void setOpenApplication(Application openApplication) {
        this.openApplication = openApplication;
    }

    public OtherContributions getOtherContributionsXMLEntity() {
        return otherContributionsXMLEntity;
    }

    public void setOtherContributionsXMLEntity(OtherContributions otherContributionsXMLEntity) {
        this.otherContributionsXMLEntity = otherContributionsXMLEntity;
    }

    public ResearchOutput getResearchOutputXMLEntity() {
        return researchOutputXMLEntity;
    }

    public void setResearchOutputXMLEntity(ResearchOutput researchOutputXMLEntity) {
        this.researchOutputXMLEntity = researchOutputXMLEntity;
    }

    public int getWizardActiveTab() {
        return wizardActiveTab;
    }

    public void setWizardActiveTab(int wizardActiveTab) {
        this.wizardActiveTab = wizardActiveTab;
    }
        
    public void addInfromationToCV()
    {
        MessageUtil.CreateGlobalFacesMessage("Information added!","The information has been added to CV.", FacesMessage.SEVERITY_INFO);
    }
    
    public void addInfromationToApplication()
    {
        MessageUtil.CreateGlobalFacesMessage("Information added!","The information has been added to the application.", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToAcademicQualificationList()
    {
        academicQualificationList.add(currentQualification);
        currentQualification = new AcademicQualification();
        MessageUtil.CreateGlobalFacesMessage("Item added!","The academic qualfication has been added to the list.", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToExperienceList()
    {
        experienceList.add(currentExperience);
        currentExperience = new Experience();
        MessageUtil.CreateGlobalFacesMessage("Item added!","The work experience item has been added to the list.", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToResearchOutputReferences()
    {
        researchOutputXMLEntity.getReferences().add(currentReference);
        currentReference = new Reference();
        MessageUtil.CreateGlobalFacesMessage("Reference added!", "The research output reference has been added to the list!", FacesMessage.SEVERITY_INFO);
        
    }
    
    public void addToOtherContributionItems()
    {
        otherContributionsXMLEntity.getItems().add(currentItem);
        currentItem = new Item();
        MessageUtil.CreateGlobalFacesMessage("Contribution added!", "The other contribution item has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToTeamMembersList()
    {
        informationXMLEntity.getTeamMembers().getMember().add(currentMember);
        currentMember = new Member();
        MessageUtil.CreateGlobalFacesMessage("Team member added!", "The team member has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToProjectAimsList()
    {
        informationXMLEntity.getProjectAims().getAim().add(currentAim);
        currentAim = "";
        MessageUtil.CreateGlobalFacesMessage("Project aim added!", "The project aim has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToExpectedOutcomesList()
    {
        informationXMLEntity.getExpectedOutcomes().getOutcome().add(currentExpectedOutcome);
        currentExpectedOutcome = "";
        MessageUtil.CreateGlobalFacesMessage("Expected outcome added!", "The expected outcome has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
        
    public void completeCV()
    {
        try
        {
            if(academicQualificationList.size() < 1)
            {
               throw new Exception("You need to have at least one academic qualification.");
            }
            if(experienceList.size() < 1)
            {
                throw new Exception("You need to have at least one work experience.");
            }
            if(otherContributionsXMLEntity.getItems().size() < 1)
            {
                throw new Exception("You need to have at least one other conttribution.");
            }            
            if(researchOutputXMLEntity.getReferences().size() < 1)
            {
                throw new Exception("You need to have at least one reference.");
            }
            
            Session session = sessionManagerBean.getSession();
            
            cv.setAcademicQualificationList(academicQualificationList);
            cv.setExperienceList(experienceList);
            cv.setResearchOutputXMLEntity(researchOutputXMLEntity);
            cv.setOtherContributionsXMLEntity(otherContributionsXMLEntity);
            cv.setAdditionalInformationXMLEntity(additionalInformationXMLEntity);
            cv.setCvID(session.getUser().getSystemID());
            cv.setPerson(session.getUser());
            grantHolderFinalisationServiceLocal.createGrantHolderCV(sessionManagerBean.getSession(), cv);
            
            wizardActiveTab++;
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
        }        
    }
    
    public void completeApplication()
    {
        try
        {
            if(informationXMLEntity.getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one project aim.");
            }
            
            if(informationXMLEntity.getExpectedOutcomes().getOutcome().size() < 1)
            {
                throw new Exception("You need at least one expected project outcome.");
            }
            
            openApplication.setInformationXMLEntity(informationXMLEntity);          
            
            wizardActiveTab++;
            
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
        }
        
    }
    
    public String finaliseApplication()
    {
        try 
        {            
            Session session = sessionManagerBean.getSession();
            System.out.println(openApplication.getRefereeReportList().toString());
            grantHolderFinalisationServiceLocal.finaliseApplication(session, openApplication);
            
            return navigationManagerBean.goToGrantHolderFinalisationServiceApplicationSelectionView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
        
        
    }
}
