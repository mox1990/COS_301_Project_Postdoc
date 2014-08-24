/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.sessionbeans;

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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * 
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "reportBean")
@SessionScoped
public class ReportBean implements Serializable {
    private String reportType = "";
    private String[] applicationTypes = {};
    private String[] personTypes = {};
    
    @PostConstruct
    public void init()
    {
        
    }
    //TODO: Move these to an array...
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
    
    public String[] applicationTypeValues()
    {
        String[] a = {
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
        return a;
    }
    
    public String[] personTypeValues()
    {
        String[] a = {
            "title",
            "fullName",
            "surname",
            "email",
            "telephone",
            "workNumber",
            "faxNumber"
        };
        return a;
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
                    if(applicationTypes.length == 0 || applicationTypes.length == 9)
                    {
                        stream = new ByteArrayInputStream(reportEJB.exportAllApplicationToPdf(sessionManagerBean.getSession()));
                    }
                    else
                    {
                        List<Application> applications = new ArrayList<>();
                        for(String type: applicationTypes)
                        {
                            applications.addAll(reportEJB.getAllApplicationsWithStatus(type));
                        }
                        stream = new ByteArrayInputStream(reportEJB.exportApplicationToPdf(sessionManagerBean.getSession(), applications));
                    }
                    break;
                case "person":
                    if(personTypes.length == 0 || personTypes.length == 9)
                    {
                        stream = new ByteArrayInputStream(reportEJB.exportAllPersonsToPdf(sessionManagerBean.getSession()));
                    }
                    else
                    {
                        List<Person> persons = new ArrayList<>();
                        List<List<String>> rows = new ArrayList<List<String>>();
                        for(Person p: reportEJB.getAllPersonsWithSecurityRole(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: personTypes)
                            {
                                String val = getAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            if(!rows.contains(row))
                                rows.add(row);
                        }
                        
                        for(Person p: reportEJB.getAllPersonsWithSecurityRole(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: personTypes)
                            {
                                String val = getAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            rows.add(row);
                        }
                        
                        for(Person p: reportEJB.getAllPersonsWithSecurityRole(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: personTypes)
                            {
                                String val = getAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            rows.add(row);
                        }
                        stream = new ByteArrayInputStream(reportEJB.dynamicReport(Arrays.asList(personTypes), rows));
                    }
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
    
    private String getAttribFromPerson(String attrib, Person p)
    {
        switch(attrib)
        {
            case "title":
                return p.getTitle();
            case "fullName":
                return p.getFullName();
            case "surname":
                return p.getSurname();
            case "email":
                return p.getEmail();
            case "telephone":
                return p.getTelephoneNumber();
            case "workNumber":
                return p.getWorkNumber();
            case "faxNumber":
                return p.getFaxNumber();
            default:
                return "null";
        }
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
                    if(applicationTypes.length == 0 || applicationTypes.length == 9)
                    {
                        stream = new ByteArrayInputStream(reportEJB.exportAllApplicationToExcel(sessionManagerBean.getSession()));
                    }
                    else
                    {
                        List<Application> applications = new ArrayList<>();
                        for(String type: applicationTypes)
                        {
                            applications.addAll(reportEJB.getAllApplicationsWithStatus(type));
                        }
                        stream = new ByteArrayInputStream(reportEJB.exportApplicationToExcel(sessionManagerBean.getSession(), applications));
                    }
                    break;
                case "person":
                    if(personTypes.length == 0 || personTypes.length == 9)
                    {
                        stream = new ByteArrayInputStream(reportEJB.exportAllPersonsToExcel(sessionManagerBean.getSession()));
                    }
                    else
                    {
                        List<Person> persons = new ArrayList<>();
                        /*for(String role: personTypes)
                        {
                            for(Person p: reportEJB.getAllPersonsWithSecurityRole(role))
                            {
                                if(!persons.contains(p)) persons.add(p);
                            }
                        }*/
                        stream = new ByteArrayInputStream(reportEJB.exportPersonsToExcel(sessionManagerBean.getSession(), persons));
                    }
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
