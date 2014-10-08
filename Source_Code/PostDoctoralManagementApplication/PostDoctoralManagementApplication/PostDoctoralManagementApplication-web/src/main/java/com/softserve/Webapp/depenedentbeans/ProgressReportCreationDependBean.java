/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.CV.ProgressReportContent;
import auto.softserve.XMLEntities.CV.Reference;
import com.softserve.persistence.DBEntities.ProgressReport;
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
@Named(value = "progressReportCreationDependBean")
@Dependent
public class ProgressReportCreationDependBean implements Serializable {
    
    private ProgressReport progressReport;
    private ProgressReportContent progressReportContent;
    
    private List<Reference> selectedReferenceList;
    private Reference currentReference;
    private List<String> selectedAimList;    
    private String currentAttainedAim;
    private List<String> selectedOutcomeList;
    private String currentAttainedOutcome;
    
    /**
     * Creates a new instance of ProgressReportCreationDependBean
     */
    public ProgressReportCreationDependBean() {
    }
    
    public void init(ProgressReport progressReport)
    {
        
        if(progressReport == null)
        {
            this.progressReport = new ProgressReport();
            progressReportContent = null;
        }
        else
        {
            this.progressReport = progressReport;
            progressReportContent = progressReport.getContentXMLEntity();
        }
        
        if(progressReportContent == null)
        {
            progressReportContent = new ProgressReportContent();
            progressReportContent.setProjectAimsAttainment(new ProgressReportContent.ProjectAimsAttainment());
            progressReportContent.setProjectOutcomesAttainment(new ProgressReportContent.ProjectOutcomesAttainment());
            progressReportContent.setResearchOutput(new ProgressReportContent.ResearchOutput());
            progressReportContent.setSelfEvaluation("");

            progressReportContent.getProjectAimsAttainment().getAimAttainment();
            progressReportContent.getProjectOutcomesAttainment().getOutcomeAttainment();
        }
        
        currentReference = new Reference();
        currentAttainedAim = "";
        currentAttainedOutcome = "";
    }

    public String getCurrentAttainedAim() {
        return currentAttainedAim;
    }

    public void setCurrentAttainedAim(String currentAttainedAim) {
        this.currentAttainedAim = currentAttainedAim;
    }

    public String getCurrentAttainedOutcome() {
        return currentAttainedOutcome;
    }

    public void setCurrentAttainedOutcome(String currentAttainedOutcome) {
        this.currentAttainedOutcome = currentAttainedOutcome;
    }

    public Reference getCurrentReference() {
        return currentReference;
    }

    public void setCurrentReference(Reference currentReference) {
        this.currentReference = currentReference;
    }

    public ProgressReport getProgressReport() {
        return progressReport;
    }

    public void setProgressReport(ProgressReport progressReport) {
        this.progressReport = progressReport;
    }

    public ProgressReportContent getProgressReportContent() {
        return progressReportContent;
    }

    public void setProgressReportContent(ProgressReportContent progressReportContent) {
        this.progressReportContent = progressReportContent;
    }

    public List<String> getSelectedAimList() {
        return selectedAimList;
    }

    public void setSelectedAimList(List<String> selectedAimList) {
        this.selectedAimList = selectedAimList;
    }

    public List<String> getSelectedOutcomeList() {
        return selectedOutcomeList;
    }

    public void setSelectedOutcomeList(List<String> selectedOutcomeList) {
        this.selectedOutcomeList = selectedOutcomeList;
    }

    public List<Reference> getSelectedReferenceList() {
        return selectedReferenceList;
    }

    public void setSelectedReferenceList(List<Reference> selectedReferenceList) {
        this.selectedReferenceList = selectedReferenceList;
    }
            
    public void addToAttainedAimsList()
    {
        progressReportContent.getProjectAimsAttainment().getAimAttainment().add(currentAttainedAim);
        currentAttainedAim = "";
        MessageUtil.CreateGlobalFacesMessage("Attained aim added!", "The project attained aim has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromAttainedAimsList()
    {
        if(selectedAimList.size() > 0)
        {
            progressReportContent.getProjectAimsAttainment().getAimAttainment().removeAll(selectedAimList);
            selectedAimList = new ArrayList<String>();
            MessageUtil.CreateGlobalFacesMessage("Attained aims removed!","The selected attained aims have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No attained aims selected!", "You have to select attained aims which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToAttainedOutcomesList()
    {
        progressReportContent.getProjectOutcomesAttainment().getOutcomeAttainment().add(currentAttainedOutcome);
        currentAttainedOutcome = "";
        MessageUtil.CreateGlobalFacesMessage("Attained outcome added!", "The attained outcome has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromAttainedOutcomesList()
    {
        if(selectedOutcomeList.size() > 0)
        {
            progressReportContent.getProjectOutcomesAttainment().getOutcomeAttainment().removeAll(selectedOutcomeList);
            selectedOutcomeList = new ArrayList<String>();
            MessageUtil.CreateGlobalFacesMessage("Attained outcomes removed!","The selected attained outcomes have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No attained outcomes selected!", "You have to select attained outcomes which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToResearchOutputList()
    {
        progressReportContent.getResearchOutput().getReferences().add(currentReference);
        currentReference = new Reference();
        MessageUtil.CreateGlobalFacesMessage("Reference added!", "The research output reference has been added to the list!", FacesMessage.SEVERITY_INFO);        
    }
    
    public void removeFromResearchOutputReferences()
    {
        if(selectedReferenceList.size() > 0)
        {                        
            ArrayList<Reference> newReferences = new ArrayList<Reference>();
            
            for(Reference reference : progressReportContent.getResearchOutput().getReferences())
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
            
            progressReportContent.getResearchOutput().getReferences().clear();
            progressReportContent.getResearchOutput().getReferences().addAll(newReferences);
            
            selectedReferenceList = new ArrayList<Reference>();
            MessageUtil.CreateGlobalFacesMessage("References removed!", "The selected research output references have been removed from the list!", FacesMessage.SEVERITY_INFO);    
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No references selected!", "You have to select research output references which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addInfromation()
    {
        MessageUtil.CreateGlobalFacesMessage("Information added!","The information has been added to the progress report.", FacesMessage.SEVERITY_INFO);
    }
    
    public ProgressReport getCombinedProgressReport() throws JAXBException
    {
        progressReport.setContentXMLEntity(progressReportContent);
        return progressReport;
    }
}
