/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.PrePostConditionMethod;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "prePostCondtionMethodFilterBean")
@Dependent
public class PrePostConditionMethodFilterBean implements Serializable {
    
    private String term;
    private List<PrePostConditionMethod> originalList;
    private List<PrePostConditionMethod> filteredList;
    
    /**
     * Creates a new instance of PrePostCondtionMethodFilterBean
     */
    public PrePostConditionMethodFilterBean() {
    }
    
    public void init(List<PrePostConditionMethod> prePostConditionMethods)
    {
        filteredList = new ArrayList<PrePostConditionMethod>();
        if(prePostConditionMethods == null)
        {
            originalList = new ArrayList<PrePostConditionMethod>();
        }
        else
        {
            originalList = prePostConditionMethods;
            filteredList.addAll(prePostConditionMethods);
        }
        
        
    }

    public List<PrePostConditionMethod> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<PrePostConditionMethod> filteredList) {
        this.filteredList = filteredList;
    }

    public List<PrePostConditionMethod> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<PrePostConditionMethod> originalList) {
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
        for(PrePostConditionMethod prePostConditionMethod : originalList)
        {
            if(prePostConditionMethod.getMethodClassName().contains(term) || prePostConditionMethod.getMethodName().contains(term) || prePostConditionMethod.getScriptLangName().contains(term) )
            {
                filteredList.add(prePostConditionMethod);
            }            
        }
    }
}
