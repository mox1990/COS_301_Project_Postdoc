/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.fellow.ProgressReportContent;
import auto.softserve.XMLEntities.fellow.Reference;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.Webapp.util.MessageUtil;
import com.softserve.ejb.ProgressReportManagementServiceLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "progressReportCreationRequestBean")
@RequestScoped
public class ProgressReportCreationRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    
    private ProgressReport progressReport;
    private ProgressReportContent progressReportContent;
    
    private Reference currentReference;
    private String currentAttainedAim;
    private String currentAttainedOutcome;
    
    /**
     * Creates a new instance of ProgressReportCreationRequestBean
     */
    public ProgressReportCreationRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        progressReport = new ProgressReport();
        progressReportContent = new ProgressReportContent();
        progressReportContent.setProjectAimsAttainment(new ProgressReportContent.ProjectAimsAttainment());
        progressReportContent.setProjectOutcomesAttainment(new ProgressReportContent.ProjectOutcomesAttainment());
        progressReportContent.setResearchOutput(new ProgressReportContent.ResearchOutput());
        progressReportContent.setSelfEvaluation("");
        
        currentAttainedAim = "";
        currentAttainedOutcome = "";
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
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
    
    public String preformProgressReportCreationRequest()
    {
        try
        {
            progressReport.setContentXMLEntity(progressReportContent);
            progressReportManagementServiceLocal.createProgressReport(sessionManagerBean.getSession(), getSelectedApplication(), progressReport);
            return navigationManagerBean.goToProgressReportManagementServiceApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(ProgressReportCreationRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
}
