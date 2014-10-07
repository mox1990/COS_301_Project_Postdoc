/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.applicationbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "generalInformationBean")
@ApplicationScoped
public class generalInformationBean {
    
    private List<String> titleSelectItems;
    private List<SelectItem> referenceTypeItems;
    private List<SelectItem> contributionTypeItems;
    private List<SelectItem> degreeTypeItems;
    private List<SelectItem> genderItems;
    private List<SelectItem> raceItems;
    private List<SelectItem> appointmentStatusItems;
    private List<SelectItem> fundingCostTypeItems;
    private List<SelectItem> fundingTypeItems;
    private List<SelectItem> citizenshipTypeItems;
    private List<SelectItem> nrfRatingsItems;
    private List<SelectItem> referneceStatusItems;
    
    private List<SelectItem> queryTypeItems;
    /**
     * Creates a new instance of generalInformationBean
     */
    public generalInformationBean() {
    }
    
     @PostConstruct
    public void init()
    {
        
        /*titleSelectItems = new ArrayList<SelectItem>();
        titleSelectItems.add(new SelectItem("Mr.","Mr."));
        titleSelectItems.add(new SelectItem("Ms.","Ms."));
        titleSelectItems.add(new SelectItem("Miss.","Miss."));
        titleSelectItems.add(new SelectItem("Mrs.","Mrs."));
        titleSelectItems.add(new SelectItem("Prof.","Prof."));
        titleSelectItems.add(new SelectItem("Dr.","Dr."));*/
        
        titleSelectItems = new ArrayList<String>();
        titleSelectItems.add("Mr.");
        titleSelectItems.add("Ms.");
        titleSelectItems.add("Miss.");
        titleSelectItems.add("Mrs.");
        titleSelectItems.add("Prof.");
        titleSelectItems.add("Dr.");
        
        
        referenceTypeItems = new ArrayList<SelectItem>();
        referenceTypeItems.add(new SelectItem("Publications in peer-reviewed/refereed journals","Publications in peer-reviewed/refereed journals"));
        referenceTypeItems.add(new SelectItem("Books and/or chapters in books","Books and/or chapters in books"));
        referenceTypeItems.add(new SelectItem("Published full-length conference papers/keynote addresses","Published full-length conference papers/keynote addresses"));
        referenceTypeItems.add(new SelectItem("Non-refereed scientific publications or popular scientific articles","Non-refereed scientific publications or popular scientific articles"));
        referenceTypeItems.add(new SelectItem("Technical/Policy reports","Technical/Policy reports"));
        referenceTypeItems.add(new SelectItem("Patents","Patents"));
        
        contributionTypeItems = new ArrayList<SelectItem>();
        contributionTypeItems.add(new SelectItem("Participation in conferences, workshops and short courses","Participation in conferences, workshops and short courses"));
        contributionTypeItems.add(new SelectItem("Teamwork and collaboration with others","Teamwork and collaboration with others"));
        contributionTypeItems.add(new SelectItem("Membership of national and international bodies","Membership of national and international bodies"));
        contributionTypeItems.add(new SelectItem("Visits as a researcher to universities or research institutes","Visits as a researcher to universities or research institutes"));
        
        degreeTypeItems = new ArrayList<SelectItem>();
        degreeTypeItems.add(new SelectItem("Doctoral (PhD)","Doctoral (PhD)"));
        degreeTypeItems.add(new SelectItem("Masters (M)","Masters (M)"));
        degreeTypeItems.add(new SelectItem("Honours (Hon)","Honours (Hon)"));
        
        genderItems = new ArrayList<SelectItem>();
        genderItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_GENDER_MALE,"Male"));
        genderItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_GENDER_FEMALE,"Female"));
        genderItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_GENDER_OTHER,"Other"));
        
        fundingCostTypeItems = new ArrayList<SelectItem>();
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_FELLOWSHIP,"Fellowship"));
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_TRAVEL,"Travel"));
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_EQUIPMENT,"Equipment"));
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_OPERATING,"Operating"));
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_RUNNING,"Running"));
        fundingCostTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_CONFERENCE,"Conference"));
        
        fundingTypeItems = new ArrayList<SelectItem>();
        fundingTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_EXTERNALLYFUNDED,com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_EXTERNALLYFUNDED));
        fundingTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPHDPOSTDOC,com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPHDPOSTDOC));
        fundingTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC,com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC));
        
        raceItems = new ArrayList<SelectItem>();
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_WHITE,"White"));
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_BLACK,"Black"));
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_COLOURED,"Coloured"));
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_ASIAN,"Asian"));
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_INDIAN,"Indian"));
        raceItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_RACE_OTHER,"Other"));
        
        appointmentStatusItems = new ArrayList<SelectItem>();
        appointmentStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.EMPLOYEE_APPOINTMENT_STATUS_FULLTIME,"Full time"));
        appointmentStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.EMPLOYEE_APPOINTMENT_STATUS_PARTTIME,"Part time"));
        
        citizenshipTypeItems = new ArrayList<SelectItem>();
        citizenshipTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_CITIZENSHIP_SOUTHAFRICAN,"South African"));
        citizenshipTypeItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_CITIZENSHIP_OTHER,"Other"));
        
        nrfRatingsItems = new ArrayList<SelectItem>();
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_A,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_A));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_B1,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_B1));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_B2,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_B2));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C1,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C1));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C2,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C2));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C3,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_C3));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_P,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_P));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_Y,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_Y));
        nrfRatingsItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_NRF_RATING_NA,com.softserve.constants.PersistenceConstants.CV_NRF_RATING_NA));
        
        referneceStatusItems = new ArrayList<SelectItem>();
        referneceStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_REFERENCE_INPROGRESS,com.softserve.constants.PersistenceConstants.CV_REFERENCE_INPROGRESS));
        referneceStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_REFERENCE_SUBMITTED,com.softserve.constants.PersistenceConstants.CV_REFERENCE_SUBMITTED));
        referneceStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_REFERENCE_UNDERREVIEW,com.softserve.constants.PersistenceConstants.CV_REFERENCE_UNDERREVIEW));
        referneceStatusItems.add(new SelectItem(com.softserve.constants.PersistenceConstants.CV_REFERENCE_PUBLISHED,com.softserve.constants.PersistenceConstants.CV_REFERENCE_PUBLISHED));
        
        queryTypeItems = new ArrayList<SelectItem>();
        queryTypeItems.add(new SelectItem(com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_USERS,com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_USERS));
        queryTypeItems.add(new SelectItem(com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_APPLICATION,com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_APPLICATION));
        queryTypeItems.add(new SelectItem(com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_AUDIT_LOG_ENTRIES,com.softserve.Webapp.constants.WebappConstants.QUERY_ALL_AUDIT_LOG_ENTRIES));
    }

    public List<String> getTitleSelectItems() {
        return titleSelectItems;
    }

    public List<SelectItem> getContributionTypeItems() {
        return contributionTypeItems;
    }

    public List<SelectItem> getDegreeTypeItems() {
        return degreeTypeItems;
    }

    public List<SelectItem> getReferenceTypeItems() {
        return referenceTypeItems;
    }

    public List<SelectItem> getGenderItems() {
        return genderItems;
    }

    public List<SelectItem> getAppointmentStatusItems() {
        return appointmentStatusItems;
    }


    public List<SelectItem> getFundingCostTypeItems() {
        return fundingCostTypeItems;
    }


    public List<SelectItem> getRaceItems() {
        return raceItems;
    }

    public List<SelectItem> getFundingTypeItems() {
        return fundingTypeItems;
    }

    public List<SelectItem> getCitizenshipTypeItems() {
        return citizenshipTypeItems;
    }

    public List<SelectItem> getNrfRatingsItems() {
        return nrfRatingsItems;
    }

    public List<SelectItem> getReferneceStatusItems() {
        return referneceStatusItems;
    }

    public List<SelectItem> getQueryTypeItems() {
        return queryTypeItems;
    }
    
    
        
    public String getVersion()
    {
        return com.softserve.constants.SystemConstants.VERSION;
    }
    
    public String getJSFVersion()
    {
        return "JSF Implementation: " + FacesContext.class.getPackage().getImplementationVendor()+ " " + FacesContext.class.getPackage().getImplementationTitle() + " " + FacesContext.class.getPackage().getImplementationVersion();
    }
    
}
