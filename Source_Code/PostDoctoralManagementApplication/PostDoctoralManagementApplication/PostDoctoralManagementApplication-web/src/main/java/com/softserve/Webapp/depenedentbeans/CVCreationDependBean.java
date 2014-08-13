/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.CV.AdditionalInformation;
import auto.softserve.XMLEntities.CV.Item;
import auto.softserve.XMLEntities.CV.OtherContributions;
import auto.softserve.XMLEntities.CV.Reference;
import auto.softserve.XMLEntities.CV.ResearchOutput;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Experience;
import com.softserve.Webapp.util.MessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "cvCreationDependBean")
@Dependent
public class CVCreationDependBean implements Serializable{

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
    
    
    /**
     * Creates a new instance of CVCreationDependBean
     */
    public CVCreationDependBean() {
    }
    
    public void init(Cv cv)
    {
        if(cv == null)
        {
            this.cv = new Cv();

            additionalInformationXMLEntity = new AdditionalInformation();
            otherContributionsXMLEntity = new OtherContributions();
            researchOutputXMLEntity = new ResearchOutput();


            academicQualificationList = new ArrayList<AcademicQualification>();
            experienceList = new ArrayList<Experience>();
        }
        else
        {
            this.cv = cv;
            
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
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
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

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
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
    
    public Cv getCombinedCv() throws JAXBException
    {
        cv.setAcademicQualificationList(academicQualificationList);
        cv.setAdditionalInformationXMLEntity(additionalInformationXMLEntity);
        cv.setExperienceList(experienceList);
        cv.setOtherContributionsXMLEntity(otherContributionsXMLEntity);
        cv.setResearchOutputXMLEntity(researchOutputXMLEntity);
        
        return cv;
    }
    
}
