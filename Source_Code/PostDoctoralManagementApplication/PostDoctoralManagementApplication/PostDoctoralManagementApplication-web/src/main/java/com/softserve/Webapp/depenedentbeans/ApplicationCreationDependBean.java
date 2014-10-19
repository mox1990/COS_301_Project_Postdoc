/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.application.ApplicationInformation;
import auto.softserve.XMLEntities.application.Member;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Experience;
import com.softserve.persistence.DBEntities.Person;
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
    private List<Member> selectedMemberList;
    private List<String> selectedAimList;
    private List<String> selectedOutcomeList;
    
    private Member currentMember;
    private String currentAim;
    private String currentExpectedOutcome;
    
    private Person grantHolder;
    private Person fellow;
    
    private List<Person> referees;
    private List<Person> selectedRefereeList;
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
            grantHolder.setUpEmployee(true);
        }
        
        
        if(fellow == null)
        {
            fellow = new Person();
            fellow.setTitle("Mr");
            fellow.setUpEmployee(true);
        }
        
        if(referees == null)
        {
            referees = new ArrayList<Person>();
        }
        selectedRefereeList = new ArrayList<Person>();
        selectedAimList = new ArrayList<String>();
        selectedMemberList = new ArrayList<Member>();
        selectedOutcomeList = new ArrayList<String>();
        
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

    public void setSelectedAimList(List<String> selectedAimList) {
        this.selectedAimList = selectedAimList;
    }

    public List<String> getSelectedAimList() {
        return selectedAimList;
    }

    public void setSelectedMemberList(List<Member> selectedMemberList) {
        this.selectedMemberList = selectedMemberList;
    }

    public List<Member> getSelectedMemberList() {
        return selectedMemberList;
    }

    public void setSelectedOutcomeList(List<String> selectedOutcomeList) {
        this.selectedOutcomeList = selectedOutcomeList;
    }

    public List<String> getSelectedOutcomeList() {
        return selectedOutcomeList;
    }

    public void setSelectedRefereeList(List<Person> selectedRefereeList) {
        this.selectedRefereeList = selectedRefereeList;
    }

    public List<Person> getSelectedRefereeList() {
        return selectedRefereeList;
    }

    public Person getFellow() {
        return fellow;
    }

    public void setFellow(Person fellow) {
        this.fellow = fellow;
    }
    
    
    
    
    public void addToTeamMembersList()
    {
        informationXMLEntity.getTeamMembers().getMember().add(currentMember);
        currentMember = new Member();
        MessageUtil.CreateGlobalFacesMessage("Team member added!", "The team member has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromTeamMembersList()
    {
        if(selectedMemberList.size() > 0)
        {
            
            ArrayList<Member> newMembers = new ArrayList<Member>();
            
            for(Member member : informationXMLEntity.getTeamMembers().getMember())
            {
                String removeValue = member.getTitle()+ " " + member.getFullName() + " " + member.getSurname() + " " + member.getDegreeType();
                
                boolean found = false;
                
                for(Member member1 : selectedMemberList)
                {                    
                    String value = member1.getTitle()+ " " + member1.getFullName() + " " + member1.getSurname() + " " + member1.getDegreeType();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newMembers.add(member);
                }
            }
            
            informationXMLEntity.getTeamMembers().getMember().clear();
            informationXMLEntity.getTeamMembers().getMember().addAll(newMembers);
            
            selectedMemberList = new ArrayList<Member>();
            MessageUtil.CreateGlobalFacesMessage("Team members removed!","The selected team members have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No team members selected!", "You have to select team members which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    } 
    
    public void addToProjectAimsList()
    {
        informationXMLEntity.getProjectAims().getAim().add(currentAim);
        currentAim = "";
        MessageUtil.CreateGlobalFacesMessage("Project aim added!", "The project aim has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromProjectAimsList()
    {
        if(selectedAimList.size() > 0)
        {
            informationXMLEntity.getProjectAims().getAim().removeAll(selectedAimList);
            selectedAimList = new ArrayList<String>();
            MessageUtil.CreateGlobalFacesMessage("Project aims removed!","The selected project aims have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No project aims selected!", "You have to select project aims which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToExpectedOutcomesList()
    {
        informationXMLEntity.getExpectedOutcomes().getOutcome().add(currentExpectedOutcome);
        currentExpectedOutcome = "";
        MessageUtil.CreateGlobalFacesMessage("Expected outcome added!", "The expected outcome has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromExpectedOutcomesList()
    {
        if(selectedOutcomeList.size() > 0)
        {
            informationXMLEntity.getExpectedOutcomes().getOutcome().removeAll(selectedOutcomeList);
            selectedOutcomeList = new ArrayList<String>();
            MessageUtil.CreateGlobalFacesMessage("Expected outcomes removed!","The selected expected outcomes have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No expected outcomes selected!", "You have to select expected outcomes which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void addToRefereesList()
    {
        referees.add(currentReferee);
        currentReferee = new Person();
        MessageUtil.CreateGlobalFacesMessage("Referee added!", "The referee has been added to the list!", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFromRefereesList()
    {
        if(selectedRefereeList.size() > 0)
        {
            for(Person referee : selectedRefereeList)
            {
                String removeValue = referee.getEmail();
                for(int i = 0; i < referees.size(); i++)
                {
                    Person referee1 = referees.get(i);
                    String value = referee1.getEmail();
                    if(removeValue.equals(value))
                    {
                        referees.remove(i);
                    }
                }
            }
            selectedRefereeList = new ArrayList<Person>();
            MessageUtil.CreateGlobalFacesMessage("Referees removed!","The selected referees have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No referees selected!", "You have to select referees which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
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
