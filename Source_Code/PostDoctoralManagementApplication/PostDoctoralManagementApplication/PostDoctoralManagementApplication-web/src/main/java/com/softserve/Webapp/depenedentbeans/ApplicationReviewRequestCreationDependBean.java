/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.Person;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationReviewRequestCreationDependBean")
@Dependent
public class ApplicationReviewRequestCreationDependBean implements Serializable {
    
    private List<Person> personSelectionList;
    private Person person;
    private Boolean manualSpecification;
    
    /**
     * Creates a new instance of ApplicationReviewRequestCreationDependBean
     */
    public ApplicationReviewRequestCreationDependBean() {
    }
    
    public void init(List<Person> persons)
    {
        person = new Person();
        manualSpecification = false;
        personSelectionList = persons;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setManualSpecification(Boolean manualSpecification) {
        this.manualSpecification = manualSpecification;
    }
    
    public Boolean getManualSpecification() {
        return manualSpecification;
    }

    public Boolean isManualSpecification() {
        return manualSpecification;
    }

    public List<Person> getPersonSelectionList() {
        return personSelectionList;
    }

    public void setPersonSelectionList(List<Person> personSelectionList) {
        this.personSelectionList = personSelectionList;
    }
    
    public void updateSelectPerson()
    {
        System.out.println(person.toString());
        if(personSelectionList.contains(person))
        {
            person = personSelectionList.get(personSelectionList.indexOf(person));
        }
        
    }
    
    
    
}
