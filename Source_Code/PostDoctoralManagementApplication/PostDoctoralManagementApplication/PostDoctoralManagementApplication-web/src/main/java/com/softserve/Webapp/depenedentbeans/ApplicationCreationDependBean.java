/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.application.Member;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.Webapp.util.MessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationCreationDependBean")
@Dependent
public class ApplicationCreationDependBean implements Serializable{
    
    private Application application;
    
    private ApplicationInformation informationXMLEntity;
    private Member currentMember;
    private String currentAim;
    private String currentExpectedOutcome;
    
    private Person grantHolder;
    
    private List<Person> referees;
    private Person currentReferee;
    
    /**
     * Creates a new instance of ApplicationCreationRequestBean
     */
    public ApplicationCreationDependBean() {
    }
    
    public void init(Application application)
    {
        if(application == null)
        {
            this.application = new Application();
            informationXMLEntity = new ApplicationInformation();           
        }
        else
        {
            this.application = application;
            informationXMLEntity = application.getInformationXMLEntity();
            referees = application.getPersonList();
            grantHolder = application.getGrantHolder();
        }
        
        if(informationXMLEntity.getTeamMembers() == null)
        {
            informationXMLEntity.setTeamMembers(new ApplicationInformation.TeamMembers());
        }
        
        if(informationXMLEntity.getProjectAims()== null)
        {
            informationXMLEntity.setProjectAims(new ApplicationInformation.ProjectAims());
        }
        
        if(informationXMLEntity.getExpectedOutcomes()== null)
        {
            informationXMLEntity.setExpectedOutcomes(new ApplicationInformation.ExpectedOutcomes());
        }
        
        if(grantHolder == null)
        {
            grantHolder = new Person();
            grantHolder.setTitle("Mr");
        }
        
        if(referees == null)
        {
            referees = new ArrayList<Person>();
        }
        
        currentMember = new Member();
        currentAim = "";
        currentExpectedOutcome = "";
        currentReferee = new Person();
        currentReferee.setTitle("Mr");
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
        
    public String getCurrentAim() {
        return currentAim;
    }

    public void setCurrentAim(String currentAim) {
        this.currentAim = currentAim;
    }

    public String getCurrentExpectedOutcome() {
        return currentExpectedOutcome;
    }

    public void setCurrentExpectedOutcome(String currentExpectedOutcome) {
        this.currentExpectedOutcome = currentExpectedOutcome;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    public void setCurrentMember(Member currentMember) {
        this.currentMember = currentMember;
    }

    public Person getCurrentReferee() {
        return currentReferee;
    }

    public void setCurrentReferee(Person currentReferee) {
        this.currentReferee = currentReferee;
    }

    public Person getGrantHolder() {
        return grantHolder;
    }

    public void setGrantHolder(Person grantHolder) {
        this.grantHolder = grantHolder;
    }

    public ApplicationInformation getInformationXMLEntity() {
        return informationXMLEntity;
    }

    public void setInformationXMLEntity(ApplicationInformation informationXMLEntity) {
        this.informationXMLEntity = informationXMLEntity;
    }

    public List<Person> getReferees() {
        return referees;
    }

    public void setReferees(List<Person> referees) {
        this.referees = referees;
    }
    
    public void addToTeamMembersList()
    {
        informationXMLEntity.getTeamMembers().getMember().add(currentMember);
        currentMember = new Member();
        MessageUtil.CreateGlobalFacesMessage("Team member added!", "The team member has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToProjectAimsList()
    {
        informationXMLEntity.getProjectAims().getAim().add(currentAim);
        currentAim = "";
        MessageUtil.CreateGlobalFacesMessage("Project aim added!", "The project aim has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToExpectedOutcomesList()
    {
        informationXMLEntity.getExpectedOutcomes().getOutcome().add(currentExpectedOutcome);
        currentExpectedOutcome = "";
        MessageUtil.CreateGlobalFacesMessage("Expected outcome added!", "The expected outcome has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addToRefereesList()
    {
        referees.add(currentReferee);
        currentReferee = new Person();
        MessageUtil.CreateGlobalFacesMessage("Referee added!", "The referee has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void addInfromationToApplication()
    {
        MessageUtil.CreateGlobalFacesMessage("Information added!","The information has been added to the application.", FacesMessage.SEVERITY_INFO);
    } 
    
    public Application getCombinedApplication() throws JAXBException
    {        
        application.setInformationXMLEntity(informationXMLEntity);
        application.setPersonList(referees);
        application.setGrantHolder(grantHolder);
        
        return application;       
    }
}
