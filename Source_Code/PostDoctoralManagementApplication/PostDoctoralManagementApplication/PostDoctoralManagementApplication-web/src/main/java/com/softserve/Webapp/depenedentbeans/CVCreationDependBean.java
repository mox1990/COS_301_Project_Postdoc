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
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Experience;
import com.softserve.Webapp.util.MessageUtil;
import com.sun.xml.xsom.impl.scd.Iterators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "cvCreationDependBean")
@Dependent
public class CVCreationDependBean implements Serializable{

    @Inject
    private GoogleScholarDependBean googleScholarDependBean;
    
    private Cv cv;
    
    private List<AcademicQualification> academicQualificationList;
    private List<AcademicQualification> selectedAcademicQualificationList;
    private AcademicQualification currentQualification;
    
    private List<Experience> experienceList;
    private List<Experience> selectedExperienceList;
    private Experience currentExperience;
    
    private ResearchOutput researchOutputXMLEntity;
    private List<Reference> selectedReferenceList;
    private Reference currentReference;
    
    private OtherContributions otherContributionsXMLEntity;
    private List<Item> selectedItemList;
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
        
        selectedAcademicQualificationList = new ArrayList<AcademicQualification>();
        selectedExperienceList = new ArrayList<Experience>();
        selectedItemList = new ArrayList<Item>();
        selectedReferenceList = new ArrayList<Reference>();
        
        currentQualification = new AcademicQualification();
        currentExperience = new Experience();
        currentItem = new Item();
        currentReference = new Reference();
        currentReference.setStatus("");
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

    public void setSelectedAcademicQualificationList(List<AcademicQualification> selectedAcademicQualificationList) {
        this.selectedAcademicQualificationList = selectedAcademicQualificationList;
    }

    public List<AcademicQualification> getSelectedAcademicQualificationList() {
        return selectedAcademicQualificationList;
    }

    public void setSelectedExperienceList(List<Experience> selectedExperienceList) {
        this.selectedExperienceList = selectedExperienceList;
    }

    public List<Experience> getSelectedExperienceList() {
        return selectedExperienceList;
    }

    public void setSelectedItemList(List<Item> selectedItemList) {
        System.out.println("This thing don't work..." + selectedItemList.size());
        this.selectedItemList = selectedItemList;
    }

    public List<Item> getSelectedItemList() {
        System.out.println("THis one does");
        return selectedItemList;
    }

    public void setSelectedReferenceList(List<Reference> selectedReferenceList) {
        this.selectedReferenceList = selectedReferenceList;
    }

    public List<Reference> getSelectedReferenceList() {
        return selectedReferenceList;
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
        MessageUtil.CreateGlobalFacesMessage("Qualifications added!","The academic qualfication has been added to the list.", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromAcademicQualificationList()
    {
        
        System.out.println("==========================Size: " + academicQualificationList.size() + " " + selectedAcademicQualificationList.size());
        if(selectedAcademicQualificationList.size() > 0)
        {            
            ArrayList<AcademicQualification> newAcademicQualifications = new ArrayList<AcademicQualification>();
            
            for(AcademicQualification qualification : academicQualificationList)
            {
                String removeValue = qualification.getName() + " " + qualification.getFieldOfStudy() + " " + qualification.getInstitution();
                
                boolean found = false;
                
                for(AcademicQualification qualification1 : selectedAcademicQualificationList)
                {                    
                    String value = qualification1.getName() + " " + qualification1.getFieldOfStudy() + " " + qualification1.getInstitution();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newAcademicQualifications.add(qualification);
                }
            }
            
            academicQualificationList = newAcademicQualifications;
            
            selectedAcademicQualificationList = new ArrayList<AcademicQualification>();
            MessageUtil.CreateGlobalFacesMessage("Qualifications removed!","The selected academic qualfications have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No qualifications selected!", "You have to select academic qualfications which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToExperienceList()
    {
        if(currentExperience.getStartDate().before(currentExperience.getEndDate()))
        {
            experienceList.add(currentExperience);
            currentExperience = new Experience();
            MessageUtil.CreateGlobalFacesMessage("Experience added!","The work experience item has been added to the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("Incorrect date!", "The start date can not be after the end date for the work experience you wish to add!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void removeFromExperienceList()
    {
        if(experienceList.size() > 0 && selectedExperienceList.size() > 0)
        {
            
            ArrayList<Experience> newExperiences = new ArrayList<Experience>();
            
            for(Experience experience : experienceList)
            {
                String removeValue = experience.getCapacity()+ " " + experience.getOrganisation();
                
                boolean found = false;
                
                for(Experience experience1 : selectedExperienceList)
                {                    
                    String value = experience1.getCapacity()+ " " + experience1.getOrganisation();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newExperiences.add(experience);
                }
            }
            
            experienceList = newExperiences;
            selectedExperienceList = new ArrayList<Experience>();
            MessageUtil.CreateGlobalFacesMessage("Experiences removed!","The selected work experience items have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No experiences selected!", "You have to select work experience items which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }    
    
    public void addToResearchOutputReferences()
    {
        researchOutputXMLEntity.getReferences().add(currentReference);
        currentReference = new Reference();
        MessageUtil.CreateGlobalFacesMessage("Reference added!", "The research output reference has been added to the list!", FacesMessage.SEVERITY_INFO);        
    }
    
    public void removeFromResearchOutputReferences()
    {
        if(selectedReferenceList.size() > 0)
        {                        
            ArrayList<Reference> newReferences = new ArrayList<Reference>();
            
            for(Reference reference : researchOutputXMLEntity.getReferences())
            {
                String removeValue = reference.getType() + " " + reference.getPublicationName() + " " + reference.getPublisher()+ " " + reference.getPublicationISBN();
                
                boolean found = false;
                
                for(Reference reference1 : selectedReferenceList)
                {                    
                    String value = reference1.getType() + " " + reference1.getPublicationName() + " " + reference1.getPublisher()+ " " + reference1.getPublicationISBN();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newReferences.add(reference);
                }
            }
            
            researchOutputXMLEntity.getReferences().clear();
            researchOutputXMLEntity.getReferences().addAll(newReferences);
            
            selectedReferenceList = new ArrayList<Reference>();
            MessageUtil.CreateGlobalFacesMessage("References removed!", "The selected research output references have been removed from the list!", FacesMessage.SEVERITY_INFO);    
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No references selected!", "You have to select research output references which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToOtherContributionItems()
    {
        otherContributionsXMLEntity.getItems().add(currentItem);
        currentItem = new Item();
        MessageUtil.CreateGlobalFacesMessage("Contribution added!", "The other contribution item has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromOtherContributionItems()
    {        
        System.out.println("==========================Size: " + otherContributionsXMLEntity.getItems().size() + " " + selectedItemList.size());
        if(selectedItemList.size() > 0)
        {            
            ArrayList<Item> newItems = new ArrayList<Item>();
            
            for(Item item : otherContributionsXMLEntity.getItems())
            {
                String removeValue= item.getType() + " " + item.getName()+ " " + item.getDate().toString() + " " + item.getLocation();
                
                boolean found = false;
                
                for(Item item1 : selectedItemList)
                {                    
                    String value = item1.getType() + " " + item1.getName()+ " " + item1.getDate().toString()  + " " + item1.getLocation();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newItems.add(item);
                }
            }
            
            otherContributionsXMLEntity.getItems().clear();
            otherContributionsXMLEntity.getItems().addAll(newItems);
            
            selectedItemList = new ArrayList<Item>();
            MessageUtil.CreateGlobalFacesMessage("Contribution removed!", "The selected other contribution items have been removed from the list!", FacesMessage.SEVERITY_INFO);    
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No contribution selected!", "You have to select other contribution items which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
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
    
    public void useSelectedResultAsCurrentReference()
    {  
        currentReference = googleScholarDependBean.getSelectedResultAsReference();

        MessageUtil.CreateGlobalFacesMessage("Using google scholar reference!", "You have selected a google scholar reference, please correct or update the data as need before adding it!", FacesMessage.SEVERITY_INFO);        
    }

    public GoogleScholarDependBean getGoogleScholarDependBean() {
        return googleScholarDependBean;
    }

    public void setGoogleScholarDependBean(GoogleScholarDependBean googleScholarDependBean) {
        this.googleScholarDependBean = googleScholarDependBean;
    }
    
    
    
}
