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
     * Creates a new instance of NewApplicationCreationBean
     */
    public NewApplicationCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversation.begin();
        
        openApplication = new Application();
        cv = new Cv();
        
        additionalInformationXMLEntity = new AdditionalInformation();
        otherContributionsXMLEntity = new OtherContributions();
        researchOutputXMLEntity = new ResearchOutput();
        informationXMLEntity = new ApplicationInformation();        
        
        academicQualificationList = new ArrayList<AcademicQualification>();
        experienceList = new ArrayList<Experience>();
        
        currentQualification = new AcademicQualification();
        currentExperience = new Experience();
        currentItem = new Item();
        currentReference = new Reference();
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
    
    public void addToAcademicQualificationList()
    {
        academicQualificationList.add(currentQualification);
        currentQualification = new AcademicQualification();
    }
    
    public void addToExperienceList()
    {
        System.out.println("==============updated work experience references");
        experienceList.add(currentExperience);
        currentExperience = new Experience();
    }
    
    public void addToResearchOutputReferences()
    {
        System.out.println("==============updated research references");
        researchOutputXMLEntity.getReferences().add(currentReference);
        currentReference = new Reference();
        
    }
    
    public void addToOtherContributionItems()
    {
        otherContributionsXMLEntity.getItems().add(currentItem);
        currentItem = new Item();
        System.out.println("==============updated Contributions");
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
        System.out.println("==============updated experience");
    }
}
