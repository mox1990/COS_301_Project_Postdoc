/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "personFilterDependBean")
@Dependent
public class PersonFilterDependBean implements Serializable {
    
    private String term;
    private List<Person> originalList;
    private List<Person> filteredList;
    
    /**
     * Creates a new instance of PersonFilterDependBean
     */
    public PersonFilterDependBean() {
    }
    
    
    public void init(List<Person> persons)
    {
        filteredList = new ArrayList<Person>();
        if(persons == null)
        {
            originalList = new ArrayList<Person>();
        }
        else
        {
            originalList = persons;
            filteredList.addAll(persons);
        }
        
        
    }

    public List<Person> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Person> filteredList) {
        this.filteredList = filteredList;
    }

    public List<Person> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<Person> originalList) {
        this.originalList = originalList;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    
    public void filterListBy()
    {
        filteredList.clear();
        System.out.println("=======================" + term);
        for(Person person : originalList)
        {
            if(person.getSystemID().toString().contains(term) || person.getCompleteName().contains(term) || person.getEmail().contains(term) || (person.getResearchFellowInformation() != null && (person.getResearchFellowInformation().getInstitutionAssignedEmail().contains(term) || person.getResearchFellowInformation().getInstitutionAssignedID().contains(term))))
            {
                filteredList.add(person);
            }            
        }
    }
    
}
