/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ReportServicesLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author kgothatso
 */
@Named(value = "reportBean")
@RequestScoped
public class ReportBean {
    private String reportType;
    private String[] applicationTypes;
    
    private final String openStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN;
    private final String submittedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED;
    private final String declinedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED;
    private final String refereedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED;
    private final String finalisedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED;
    private final String recommendedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED;
    private final String endorsedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED;
    private final String eligibleStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE;
    private final String fundedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED;
    private final String completedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED;
    private final String terminatedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED;
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ReportServicesLocal reportEJB;
    
    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String[] getApplicationTypes() {
        return applicationTypes;
    }

    public void setApplicationTypes(String[] applicationTypes) {
        this.applicationTypes = applicationTypes;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public String getSubmittedStatus() {
        return submittedStatus;
    }

    public String getDeclinedStatus() {
        return declinedStatus;
    }

    public String getRefereedStatus() {
        return refereedStatus;
    }

    public String getFinalisedStatus() {
        return finalisedStatus;
    }

    public String getRecommendedStatus() {
        return recommendedStatus;
    }

    public String getEndorsedStatus() {
        return endorsedStatus;
    }

    public String getEligibleStatus() {
        return eligibleStatus;
    }

    public String getFundedStatus() {
        return fundedStatus;
    }

    public String getCompletedStatus() {
        return completedStatus;
    }

    public String getTerminatedStatus() {
        return terminatedStatus;
    }
    
    public StreamedContent getReportPDF() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            switch(reportType)
            {
                case "application":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportApplicationToPdf(sessionManagerBean.getSession(), reportEJB.getAllApplications()));
                    break;
                case "person":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToPdf(sessionManagerBean.getSession(), reportEJB.getAllPersons()));
                    break;
                default:
                    throw new Exception("Didn't Specify valid report type.");
            }
            
            return new DefaultStreamedContent(stream, "application/pdf", "Report-About-Date.pdf");
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ReportBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        } 
        finally 
        {
            try 
            {
                if(stream!=null) stream.close();
            } 
            catch (IOException ex) 
            {
                ExceptionUtil.logException(ReportBean.class, ex);
                ExceptionUtil.handleException(null, ex);
            }
        }
        return null;
    }
    
    public StreamedContent getReportExcel() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            switch(reportType)
            {
                case "application":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    List<Application> applications = new ArrayList<>();
                    for(String type: applicationTypes)
                    {
                        System.out.println("Adding type: " + type);
                        applications.addAll(reportEJB.getAllApplicationsWithStatus(type));
                    }
                    stream = new ByteArrayInputStream(reportEJB.exportApplicationToExcel(sessionManagerBean.getSession(), applications));
                    break;
                case "person":
                    // TODO: Come up with a discretion for the persons...
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToExcel(sessionManagerBean.getSession(), reportEJB.getAllPersons()));
                    break;
                default:
                    throw new Exception("Didn't Specify valid report type.");
            }
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", "Report-About-Date.xls");
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ReportBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        } 
        finally 
        {
            try 
            {
                if(stream!=null) stream.close();
            } 
            catch (IOException ex) 
            {
                ExceptionUtil.logException(ReportBean.class, ex);
                ExceptionUtil.handleException(null, ex);
            }
        }
        return null;
    }
    
    public boolean applicationReport()
    {
        return reportType.equals("application");
    }
    
    public boolean personReport()
    {
        return reportType.equals("person");
    }
}
