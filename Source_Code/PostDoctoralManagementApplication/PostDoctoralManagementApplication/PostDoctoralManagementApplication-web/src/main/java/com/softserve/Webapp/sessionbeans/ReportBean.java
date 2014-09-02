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
    private String[] applicationAttributes = {};
    private String[] applicationStageAttributes = {};
    
    private String[] personTypes = {};
    private String[] personAttributes = {};
    private String[] personAddressAttributes = {};
    private String[] personCvAttributes = {};
    
    @PostConstruct
    public void init()
    {
        
    }
    //TODO: Move these to an array...
    private final String P_PROSPECTIVE_FELLOW = "prospectiveFellow";
    private final String P_RESEARCH_FELLOW = "researchFellow";
    private final String P_GRANT_HOLDER = "grantHolder";
            
    // Person Address data...
    private final String ADDRESS_COUNTRY = "country";
    private final String ADDRESS_PROVINCE = "province";
    private final String ADDRESS_TOWN_CITY = "town_city";
    private final String ADDRESS_SURBURB = "suburb";
    private final String ADDRESS_STREET = "street";
    private final String ADDRESS_STREET_NUMBER = "streetNumber";
    private final String ADDRESS_ROOM_NUMBER = "roomNumber";
    private final String ADDRESS_ZIP_CODE = "zip/postalCode";
    
    // Person CV data...
    private final String CV_ID_NUMBER = "idNumber/passport";
    private final String CV_DOB = "dateOfBirth";
    private final String CV_GENDER = "gender";
    private final String CV_CITIZENSHIP = "citizenship";
    private final String CV_NRF = "NRF Rating";
    private final String CV_RACE = "Race";
    private final String CV_RECENT_INSTITUATION = "Recent Institution";
    private final String CV_RESEARCH_OUTPUT = "Research Output";
    private final String CV_OTHER_CONTRIB = "Other Contributions";
    private final String CV_ADDITIONAL = "Additional Information";
    
    // Application Attributes...
    private final String A_TYPE = "Type";
    private final String A_STATUS = "Status";
    private final String A_FUNDING_TYPE = "Funding Type";
    private final String A_TIME = "Created on";
    private final String A_SUBMISSION = "Submission Date";
    private final String A_FINALISATION = "Finalisation Date";
    private final String A_START = "Start Date";
    private final String A_END = "End Date";
    private final String A_TITLE = "Project Title";
    private final String A_INFORMATION = "Information";
    private final String A_FELLOW = "Fellow";
    private final String A_GRANTHOLDER = "Grant Holder";
    
    public String[] applicationAttribValues()
    {
        String[] a = {
            A_TYPE,
            A_STATUS,
            A_FUNDING_TYPE,
            A_TIME,
            A_SUBMISSION,
            A_FINALISATION,
            A_START,
            A_END,
            A_TITLE,
            A_INFORMATION,
            A_FELLOW ,
            A_GRANTHOLDER
        };
        return a;
    }
    
    private String getAttribFromApplication(String attrib, Application a)
    {
        switch(attrib)
        {
            case A_TYPE:
                return a.getType();
            case A_STATUS:
                return a.getStatus();
            case A_FUNDING_TYPE:
                return a.getFundingType();
            case A_TIME:
                if(a.getTimestamp() == null)
                    return "null";
                return a.getTimestamp().toString();
            case A_SUBMISSION:
                if(a.getStartDate() == null)
                    return "null";
                return a.getSubmissionDate().toString();
            case A_FINALISATION:
                if(a.getFinalisationDate() == null)
                    return "null";
                return a.getFinalisationDate().toString();
            case A_START:
                if(a.getStartDate() == null)
                    return "null";
                return a.getStartDate().toString();
            case A_END:
                if(a.getEndDate() == null)
                    return "null";
                return a.getEndDate().toString();
            case A_TITLE:
                return a.getProjectTitle();
            case A_INFORMATION:
                return a.getInformation();
            case A_FELLOW:
                if(a.getFellow() == null)
                    return "null";
                return a.getFellow().getCompleteName();
            case A_GRANTHOLDER:
                if(a.getGrantHolder()== null)
                    return "null";
                return a.getGrantHolder().getCompleteName();
            default:
                return "null";
        }
    }
    
    // Application Stage Reports
    private final String AS_FUNDING = "Funding Report";
    private final String AS_ENDORSEMENT = "Endorsement";
    private final String AS_RECOMMENDATION = "Recommendation Report";
    private final String AS_ELIGIBILTY = "Eligibilty Report";
    private final String AS_DECLINE = "Decline Report";
    
    public String[] applicationStageAttribValues()
    {
        String[] a = {
            AS_FUNDING ,
            AS_ENDORSEMENT,
            AS_RECOMMENDATION ,
            AS_ELIGIBILTY,
            AS_DECLINE
        };
        return a;
    }
    
    private String getStageAttribFromApplication(String attrib, Application a)
    {
        switch(attrib)
        {
            case AS_FUNDING:
                if(a.getFundingType() == null)
                    return "null";
                return a.getFundingReport().getTimestamp().toString() + ": " + a.getFundingReport().getFundingCostList().toString();
            case AS_ENDORSEMENT:
                if(a.getEndorsement() == null)
                    return "null";
                return a.getEndorsement().getTimestamp().toString() + ": " + a.getEndorsement().getMotivation();
            case AS_RECOMMENDATION:
                if(a.getRecommendationReport() == null)
                    return "null";
                return a.getRecommendationReport().getTimestamp().toString() + ": " + a.getRecommendationReport().getContent();
            case AS_ELIGIBILTY:
                if(a.getEligiblityReport()== null)
                    return "null";
                return a.getEligiblityReport().getEligiblityCheckDate().toString() + ": Checked by " + a.getEligiblityReport().getEligiblityChecker().getCompleteName();
            case AS_DECLINE:
                if(a.getDeclineReport()== null)
                    return "null";
                return a.getDeclineReport().getTimestamp().toString() + "(" + a.getDeclineReport().getCreator().getFullName() + ") : " + a.getDeclineReport().getReason();
            default:
                return "null";
        }
    }
    
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

    public String[] getPersonAttributes() {
        return personAttributes;
    }

    public void setPersonAttributes(String[] personTypes) {
        this.personAttributes = personTypes;
    }

    public String[] getPersonTypes() {
        return personTypes;
    }

    public void setPersonTypes(String[] personTypes) {
        this.personTypes = personTypes;
    }

    public String[] getPersonAddressAttributes() {
        return personAddressAttributes;
    }

    public void setPersonAddressAttributes(String[] personAddressAttributes) {
        this.personAddressAttributes = personAddressAttributes;
    }

    public String[] getPersonCvAttributes() {
        return personCvAttributes;
    }

    public void setPersonCvAttributes(String[] personCvAttributes) {
        this.personCvAttributes = personCvAttributes;
    }

    public String[] getApplicationAttributes() {
        return applicationAttributes;
    }

    public void setApplicationAttributes(String[] applicationAttributes) {
        this.applicationAttributes = applicationAttributes;
    }

    public String[] getApplicationStageAttributes() {
        return applicationStageAttributes;
    }

    public void setApplicationStageAttributes(String[] applicationStageAttributes) {
        this.applicationStageAttributes = applicationStageAttributes;
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
            P_PROSPECTIVE_FELLOW,
            P_RESEARCH_FELLOW,
            P_GRANT_HOLDER
        };
        return a;
    }
    
    public String[] personAttribValues()
    {
        String[] a = {
            "title",
            "fullName",
            "surname",
            "email",
            "telephoneNumber",
            "workNumber",
            "faxNumber",
            "cellphoneNumber",
            "accountStatus"
        };
        return a;
    }
    
    public String[] personAddressAttribValues()
    {
        String[] a = {
            ADDRESS_COUNTRY,
            ADDRESS_PROVINCE,
            ADDRESS_TOWN_CITY,
            ADDRESS_SURBURB,
            ADDRESS_STREET,
            ADDRESS_STREET_NUMBER,
            ADDRESS_ROOM_NUMBER,
            ADDRESS_ZIP_CODE
        };
        return a;
    }
    
    private String getAddressAttribFromPerson(String attrib, Person p)
    {
        if(p.getAddressLine1() == null) return "null";
        switch(attrib)
        {
            case ADDRESS_COUNTRY:
                return p.getAddressLine1().getCountry();
            case ADDRESS_PROVINCE:
                return p.getAddressLine1().getProvince();
            case ADDRESS_TOWN_CITY:
                return p.getAddressLine1().getTownCity();
            case ADDRESS_SURBURB:
                return p.getAddressLine1().getSuburb();
            case ADDRESS_STREET:
                return p.getAddressLine1().getStreet();
            case ADDRESS_STREET_NUMBER:
                return (p.getAddressLine1().getStreeNumber() != null)? p.getAddressLine1().getStreeNumber().toString() : "null";
            case ADDRESS_ROOM_NUMBER:
                return p.getAddressLine1().getRoomNumber();
            case ADDRESS_ZIP_CODE:
                return p.getAddressLine1().getZippostalCode();
            default:
                return "null";
        }
    }
    
    public String[] personCvAttribValues()
    {
        String[] a = {
            CV_ID_NUMBER,
            CV_DOB,
            CV_GENDER,
            CV_CITIZENSHIP,
            CV_NRF,
            CV_RACE,
            CV_RECENT_INSTITUATION,
            CV_RESEARCH_OUTPUT,
            CV_OTHER_CONTRIB,
            CV_ADDITIONAL
        };
        return a;
    }
    
    private String getCvAttribFromPerson(String attrib, Person p)
    {
        if(p.getCv() == null) return "null";
        
        switch(attrib)
        {
            case CV_ID_NUMBER:
                return p.getCv().getIdNumber();
            case CV_DOB:
                return p.getCv().getDateOfBirth().toString();
            case CV_GENDER:
                return p.getCv().getGender();
            case CV_CITIZENSHIP:
                return p.getCv().getCitizenship();    
            case CV_NRF:
                return p.getCv().getNrfRating();
            case CV_RACE:
                return p.getCv().getRace();
            case CV_RECENT_INSTITUATION:
                return p.getCv().getRecentInstitution();
            case CV_RESEARCH_OUTPUT:
                return p.getCv().getResearchOutput();
            case CV_OTHER_CONTRIB:
                return p.getCv().getOtherContributions();
            case CV_ADDITIONAL:
                return p.getCv().getAdditionalInformation();
            default:
                return "null";
        }
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
            case "telephoneNumber":
                return p.getTelephoneNumber();
            case "workNumber":
                return p.getWorkNumber();
            case "faxNumber":
                return p.getFaxNumber();
            case "cellphoneNumber":
                return p.getCellphoneNumber();
            case "accountStatus":
                return p.getAccountStatus();
            default:
                return "null";
        }
    }
    
    public StreamedContent getReportPDF() // TODO: Add report filters...
        {
        InputStream stream = null;
        try 
        {
            List<String> col = new ArrayList<>();
            List<List<String>> rows = new ArrayList<List<String>>();
            switch(reportType)
            {
                case "application":
                    List<Application> applications = new ArrayList<>();
                    for(String type: applicationTypes)
                    {
                        for(Application a: reportEJB.getAllApplicationsWithStatus(type))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: applicationAttributes)
                            {
                                String val = getAttribFromApplication(attrib, a);
                                row.add(val);
                            }
                            for(String attrib: applicationStageAttributes)
                            {
                                String val = getStageAttribFromApplication(attrib, a);
                                row.add(val);
                            }
                            if(!rows.contains(row))
                                rows.add(row);
                        }
                        
                    }
                    col.addAll(Arrays.asList(applicationAttributes));
                    col.addAll(Arrays.asList(applicationStageAttributes));
                    
                    stream = new ByteArrayInputStream(reportEJB.dynamicReport(sessionManagerBean.getSession(), col, rows, "Application Report"));
                    break;
                case "person":
                    List<Person> persons = new ArrayList<>();
                    for(String role: personTypes)
                    {
                        for(Person p: reportEJB.getAllPersonsWithSecurityRole(getSecurityRoleID(role)))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: personAttributes)
                            {
                                String val = getAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            for(String attrib: personAddressAttributes)
                            {
                                String val = getAddressAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            for(String attrib: personCvAttributes)
                            {
                                String val = getCvAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            if(!rows.contains(row))
                                rows.add(row);
                        }
                    }
                    col.addAll(Arrays.asList(personAttributes));
                    col.addAll(Arrays.asList(personAddressAttributes));
                    col.addAll(Arrays.asList(personCvAttributes));
                    
                    stream = new ByteArrayInputStream(reportEJB.dynamicReport(sessionManagerBean.getSession(), col, rows, "Person Report"));
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
    
    private Long getSecurityRoleID(String role)
    {
        switch(role)
        {
            case P_PROSPECTIVE_FELLOW:
                return com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW;
            case P_RESEARCH_FELLOW:
                return com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW;
            case P_GRANT_HOLDER:
                return com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER;
            default:
                return Long.MAX_VALUE;
        }
    }
    
    public StreamedContent getReportExcel() // TODO: Add report filters...
        {
        InputStream stream = null;
        try 
        {
            List<String> col = new ArrayList<>();
            List<List<String>> rows = new ArrayList<List<String>>();
            switch(reportType)
            {
                case "application":
                    List<Application> applications = new ArrayList<>();
                    for(String type: applicationTypes)
                    {
                        for(Application a: reportEJB.getAllApplicationsWithStatus(type))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: applicationAttributes)
                            {
                                String val = getAttribFromApplication(attrib, a);
                                row.add(val);
                            }
                            for(String attrib: applicationStageAttributes)
                            {
                                String val = getStageAttribFromApplication(attrib, a);
                                row.add(val);
                            }
                            if(!rows.contains(row))
                                rows.add(row);
                        }
                        
                    }
                    col.addAll(Arrays.asList(applicationAttributes));
                    col.addAll(Arrays.asList(applicationStageAttributes));
                    
                    stream = new ByteArrayInputStream(reportEJB.exportSpreadsheetReport(sessionManagerBean.getSession(), col, rows, "Application Report"));
                    break;
                case "person":
                    List<Person> persons = new ArrayList<>();
                    for(String role: personTypes)
                    {
                        for(Person p: reportEJB.getAllPersonsWithSecurityRole(getSecurityRoleID(role)))
                        {
                            List<String> row = new ArrayList<>();
                            for(String attrib: personAttributes)
                            {
                                String val = getAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            for(String attrib: personAddressAttributes)
                            {
                                String val = getAddressAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            for(String attrib: personCvAttributes)
                            {
                                String val = getCvAttribFromPerson(attrib, p);
                                row.add(val);
                            }
                            if(!rows.contains(row))
                                rows.add(row);
                        }
                    }
                    col.addAll(Arrays.asList(personAttributes));
                    col.addAll(Arrays.asList(personAddressAttributes));
                    col.addAll(Arrays.asList(personCvAttributes));
                    
                    stream = new ByteArrayInputStream(reportEJB.exportSpreadsheetReport(sessionManagerBean.getSession(), col, rows, "Person Report"));
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
