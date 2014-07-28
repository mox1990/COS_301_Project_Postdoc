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
    
    private List<SelectItem> titleSelectItems;
    private List<SelectItem> referenceTypeItems;
    private List<SelectItem> contributionTypeItems;
    private List<SelectItem> degreeTypeItems;
    private List<SelectItem> genderItems;
    
    
    /**
     * Creates a new instance of generalInformationBean
     */
    public generalInformationBean() {
    }
    
     @PostConstruct
    public void init()
    {
        
        titleSelectItems = new ArrayList<SelectItem>();
        titleSelectItems.add(new SelectItem("Mr.","Mr."));
        titleSelectItems.add(new SelectItem("Ms.","Ms."));
        titleSelectItems.add(new SelectItem("Miss.","Miss."));
        titleSelectItems.add(new SelectItem("Mrs.","Mrs."));
        titleSelectItems.add(new SelectItem("Prof.","Prof."));
        titleSelectItems.add(new SelectItem("Dr.","Dr."));
        
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
        genderItems.add(new SelectItem("Male","Male"));
        genderItems.add(new SelectItem("Female","Female"));
        genderItems.add(new SelectItem("Other","Other"));
    }

    public List<SelectItem> getTitleSelectItems() {
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
    
    
}
