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
    private Conversation conversation;
    
    @EJB
    private NewApplicationServiceLocal newApplicationServiceLocal;
    
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
        Session session = null;
        try 
        {        
            session = sessionManagerBean.getSession();
            openApplication = newApplicationServiceLocal.getOpenApplication(session);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToPortalView());
            return;
        }
        
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
        
        
        
        if(openApplication == null)
        {
            openApplication = new Application();
            informationXMLEntity = new ApplicationInformation();           
        }
        else
        {
            informationXMLEntity = openApplication.getInformationXMLEntity();
            referees = openApplication.getPersonList();
            grantHolder = openApplication.getGrantHolderID();
        }
        
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
        
        if(grantHolder == null)
        {
            grantHolder = new Person();
        }
        
        if(referees == null)
        {
            referees = new ArrayList<Person>();
        }
        
        currentMember = new Member();
        currentAim = "";
        currentExpectedOutcome = "";
        currentReferee = new Person();
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

    public Person getCurrentReferee() {
        return currentReferee;
    }

    public void setCurrentReferee(Person currentReferee) {
        this.currentReferee = currentReferee;
    }

    public Person getGrantHolder() {
        return grantHolder;
    }

    public void setGrantHolder(Person grantHolder) {
        this.grantHolder = grantHolder;
    }

    public List<Person> getReferees() {
        return referees;
    }

    public void setReferees(List<Person> referees) {
        this.referees = referees;
    }
        
    public void addToAcademicQualificationList()
    {
        academicQualificationList.add(currentQualification);
        currentQualification = new AcademicQualification();
    }
    
    public void addToExperienceList()
    {
        experienceList.add(currentExperience);
        currentExperience = new Experience();
    }
    
    public void addToResearchOutputReferences()
    {
        researchOutputXMLEntity.getReferences().add(currentReference);
        currentReference = new Reference();
        
    }
    
    public void addToOtherContributionItems()
    {
        otherContributionsXMLEntity.getItems().add(currentItem);
        currentItem = new Item();
    }
    
    public void addToTeamMembersList()
    {
        informationXMLEntity.getTeamMembers().getMember().add(currentMember);
        currentMember = new Member();
    }
    
    public void addToProjectAimsList()
    {
        informationXMLEntity.getProjectAims().getAim().add(currentAim);
        currentAim = "";
    }
    
    public void addToExpectedOutcomesList()
    {
        informationXMLEntity.getExpectedOutcomes().getOutcome().add(currentExpectedOutcome);
        currentExpectedOutcome = "";
    }
    
    public void addToRefereesList()
    {
        referees.add(currentReferee);
        currentReferee = new Person();
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
            newApplicationServiceLocal.createProspectiveFellowCV(sessionManagerBean.getSession(), cv);
            
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
    
    public void completeGrantHolderSpecification()
    {
        openApplication.setGrantHolderID(grantHolder);
        wizardActiveTab++;
    }
    
    public void completeRefereeSpecification()
    {
        openApplication.setPersonList(referees);
        wizardActiveTab++;
    }
    
    public String submitApplication()
    {
        try 
        {
            newApplicationServiceLocal.submitApplication(sessionManagerBean.getSession(), openApplication);
            return navigationManagerBean.goToApplicationServicesHomeView();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        } 
    }
}
