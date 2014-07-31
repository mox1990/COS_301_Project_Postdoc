/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.referee.ReferalReport;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.RefereeReportServiceLocal;
import com.softserve.system.Session;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "refereeReportCreationRequestBean")
@RequestScoped
public class RefereeReportCreationRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;    
    
    @EJB
    private RefereeReportServiceLocal refereeReportServiceLocal;
    
    private RefereeReport refereeReport;
    private ReferalReport referalReport;
    
    /**
     * Creates a new instance of RefereeReportCreationRequestBean
     */
    public RefereeReportCreationRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        refereeReport = new RefereeReport();
        referalReport = new ReferalReport();
        referalReport.setEvaluationOfCandidateSelf("");
        referalReport.setProjectSummary("");
    }

    public ReferalReport getReferalReport() {
        return referalReport;
    }

    public void setReferalReport(ReferalReport referalReport) {
        this.referalReport = referalReport;
    }

    public RefereeReport getRefereeReport() {
        return refereeReport;
    }

    public void setRefereeReport(RefereeReport refereeReport) {
        this.refereeReport = refereeReport;
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }
    
    public String preformRefereeReportCreationRequest()
    {
        try 
        {
            Session session = sessionManagerBean.getSession();
            System.out.println("============================== sum " + referalReport.getProjectSummary());
            refereeReport.setContentXMLEntity(referalReport);
            refereeReport.setReferee(session.getUser());            
            refereeReportServiceLocal.submitReferralReport(session, getSelectedApplication(), refereeReport);
            return navigationManagerBean.goToRefereeReportServiceApplicationSelectionView();
        } 
        catch (Exception e) 
        {
            ExceptionUtil.logException(RefereeReportCreationRequestBean.class, e);
            ExceptionUtil.handleException(null, e);
            return "";
        }
    }
    
}
