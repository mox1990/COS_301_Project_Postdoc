/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ReportServicesLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String reportType = "";
    private String[] applicationTypes = {
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED,
        com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED
    };
    private String[] personTypes = {""};
    
    private final String openStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN;
    private final String submittedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED;
    private final String declinedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED;
    private final String refereedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED;
    private final String finalisedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED;
    private final String recommendedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED;
    private final String endorsedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED;
    private final String eligibleStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE;
    private final String fundedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED;
    private final String completedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED;
    private final String terminatedStatus = com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED;
    
    private final String prospectiveFellow = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW);
    private final String referre = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE);
    private final String researchFellow = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW);
    private final String grantHolder = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER);
    private final String hod = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_HOD);
    private final String deansOffice = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER);
    private final String dris = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER);
    private final String postDocComitteeMember = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_POSTDOCTORAL_COMMITTEE_MEMBER);
    private final String systemAdmin = String.valueOf(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR);
    
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

    public String[] getPersonTypes() {
        return personTypes;
    }

    public void setPersonTypes(String[] personTypes) {
        this.personTypes = personTypes;
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

    // Person values
    public String getProspectiveFellow() {
        return prospectiveFellow;
    }

    public String getReferre() {
        return referre;
    }

    public String getResearchFellow() {
        return researchFellow;
    }

    public String getGrantHolder() {
        return grantHolder;
    }

    public String getHod() {
        return hod;
    }

    public String getDeansOffice() {
        return deansOffice;
    }

    public String getDris() {
        return dris;
    }

    public String getPostDocComitteeMember() {
        return postDocComitteeMember;
    }

    public String getSystemAdmin() {
        return systemAdmin;
    }
    
    public StreamedContent getReportPDF() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            switch(reportType)
            {
                case "application":
                    System.out.println("TypesGiving: " + Arrays.toString(applicationTypes));
                    List<Application> applications = new ArrayList<>();
                    for(String type: applicationTypes)
                    {
                        System.out.println("Adding type: " + type);
                        applications.addAll(reportEJB.getAllApplicationsWithStatus(type));
                    }
                    //stream = new ByteArrayInputStream(reportEJB.exportApplicationToPdf(sessionManagerBean.getSession(), applications));
                    break;
                case "person":
                    List<Person> persons = new ArrayList<>();
                    for(String role: personTypes)
                    {
                        persons.addAll(reportEJB.getAllPersonsWithSecurityRole(role));
                    }
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToPdf(sessionManagerBean.getSession(), persons));
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
                    /*for(String type: applicationTypes)
                    {
                        System.out.println("Adding type: " + type);
                        applications.addAll(reportEJB.getAllApplicationsWithStatus(type));
                    }*/
                    System.out.println("TypesGiving: " + applicationTypes);
                    stream = new ByteArrayInputStream(reportEJB.exportApplicationToExcel(sessionManagerBean.getSession(), applications));
                    break;
                case "person":
                    List<Person> persons = new ArrayList<>();
                    for(String role: personTypes)
                    {
                        persons.addAll(reportEJB.getAllPersonsWithSecurityRole(role));
                    }
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToExcel(sessionManagerBean.getSession(), persons));
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
