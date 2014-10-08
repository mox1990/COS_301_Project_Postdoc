/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.Application;
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
@Named(value = "applicationFilterDependBean")
@Dependent
public class ApplicationFilterDependBean implements Serializable {
    
    private String term;
    private List<Application> originalList;
    private List<Application> filteredList;
    
    /**
     * Creates a new instance of ApplicationFilterDependBean
     */
    public ApplicationFilterDependBean() {
    }
    
    public void init(List<Application> applications)
    {
        filteredList = new ArrayList<Application>();        
        if(applications == null)
        {
            originalList = new ArrayList<Application>();
        }
        else
        {
            originalList = applications;
            filteredList.addAll(applications);
        }
        
    }

    public List<Application> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<Application> originalList) {
        this.originalList = originalList;
    }
    
    public List<Application> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Application> filteredList) {
        this.filteredList = filteredList;
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
        for(Application application : originalList)
        {
            if(application.getApplicationID().toString().contains(term) || application.getProjectTitle().contains(term) || application.getFellow().getCompleteName().contains(term) || application.getFellow().getSystemID().contains(term) || application.getGrantHolder().getCompleteName().contains(term) || application.getGrantHolder().getSystemID().contains(term))
            {
                filteredList.add(application);
            }            
        }
    }
    
}
