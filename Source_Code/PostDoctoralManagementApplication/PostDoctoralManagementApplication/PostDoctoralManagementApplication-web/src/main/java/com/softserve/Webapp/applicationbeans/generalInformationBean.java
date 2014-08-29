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
        genderItems.add(new SelectItem("Male",com.softserve.constants.PersistenceConstants.CV_GENDER_MALE));
        genderItems.add(new SelectItem("Female",com.softserve.constants.PersistenceConstants.CV_GENDER_FEMALE));
        genderItems.add(new SelectItem("Other",com.softserve.constants.PersistenceConstants.CV_GENDER_OTHER));
        
        fundingCostTypeItems = new ArrayList<SelectItem>();
        fundingCostTypeItems.add(new SelectItem("Fellowship",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_FELLOWSHIP));
        fundingCostTypeItems.add(new SelectItem("Travel",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_TRAVEL));
        fundingCostTypeItems.add(new SelectItem("Equipment",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_EQUIPMENT));
        fundingCostTypeItems.add(new SelectItem("Operating",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_OPERATING));
        fundingCostTypeItems.add(new SelectItem("Running",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_RUNNING));
        fundingCostTypeItems.add(new SelectItem("Conference",com.softserve.constants.PersistenceConstants.FUNDINGCOST_TYPE_CONFERENCE));
        
        fundingTypeItems = new ArrayList<SelectItem>();
        fundingTypeItems.add(new SelectItem("Extenally funded",com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_EXTERNALLYFUNDED));
        fundingTypeItems.add(new SelectItem("UP PhD Postdoc",com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPHDPOSTDOC));
        fundingTypeItems.add(new SelectItem("UP Postdoc",com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC));
        
        raceItems = new ArrayList<SelectItem>();
        raceItems.add(new SelectItem("White",com.softserve.constants.PersistenceConstants.CV_RACE_WHITE));
        raceItems.add(new SelectItem("Black",com.softserve.constants.PersistenceConstants.CV_RACE_BLACK));
        raceItems.add(new SelectItem("Coloured",com.softserve.constants.PersistenceConstants.CV_RACE_COLOURED));
        raceItems.add(new SelectItem("Asian",com.softserve.constants.PersistenceConstants.CV_RACE_ASIAN));
        raceItems.add(new SelectItem("Indian",com.softserve.constants.PersistenceConstants.CV_RACE_INDIAN));
        raceItems.add(new SelectItem("Other",com.softserve.constants.PersistenceConstants.CV_RACE_OTHER));
        
        appointmentStatusItems = new ArrayList<SelectItem>();
        appointmentStatusItems.add(new SelectItem("Full time",com.softserve.constants.PersistenceConstants.EMPLOYEE_APPOINTMENT_STATUS_FULLTIME));
        appointmentStatusItems.add(new SelectItem("Part time",com.softserve.constants.PersistenceConstants.EMPLOYEE_APPOINTMENT_STATUS_PARTTIME));
        
        citizenshipTypeItems = new ArrayList<SelectItem>();
        citizenshipTypeItems.add(new SelectItem("South African",com.softserve.constants.PersistenceConstants.CV_CITIZENSHIP_SOUTHAFRICAN));
        citizenshipTypeItems.add(new SelectItem("Other",com.softserve.constants.PersistenceConstants.CV_CITIZENSHIP_OTHER));
        
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
    
    

    
}
