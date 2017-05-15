/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.fellow.ProgressReportContent;
import auto.softserve.XMLEntities.fellow.Reference;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Webapp.util.MessageUtil;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
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
    
    private Reference currentReference;
    private String currentAttainedAim;
    private String currentAttainedOutcome;
    
    /**
     * Creates a new instance of ProgressReportCreationDependBean
     */
    public ProgressReportCreationDependBean() {
    }
    
    public void init()
    {
        this.progressReport = new ProgressReport();
        progressReportContent = new ProgressReportContent();
        progressReportContent.setProjectAimsAttainment(new ProgressReportContent.ProjectAimsAttainment());
        progressReportContent.setProjectOutcomesAttainment(new ProgressReportContent.ProjectOutcomesAttainment());
        progressReportContent.setResearchOutput(new ProgressReportContent.ResearchOutput());
        progressReportContent.setSelfEvaluation("");
        
        progressReportContent.getProjectAimsAttainment().getAimAttainment();
        progressReportContent.getProjectOutcomesAttainment().getOutcomeAttainment();
        
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
    
    public void addToAttainedAimsList()
    {
        progressReportContent.getProjectAimsAttainment().getAimAttainment().add(currentAttainedAim);
        currentAttainedAim = "";
        MessageUtil.CreateGlobalFacesMessage("Attained aim added!", "The project attained aim has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToAttainedOutcomesList()
    {
        progressReportContent.getProjectOutcomesAttainment().getOutcomeAttainment().add(currentAttainedOutcome);
        currentAttainedOutcome = "";
        MessageUtil.CreateGlobalFacesMessage("Attained outcome added!", "The attained outcome has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToResearchOutputList()
    {
        progressReportContent.getResearchOutput().getReferences().add(currentReference);
        currentReference = new Reference();
        MessageUtil.CreateGlobalFacesMessage("Reference added!", "The research output reference has been added to the list!", FacesMessage.SEVERITY_INFO);        
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
