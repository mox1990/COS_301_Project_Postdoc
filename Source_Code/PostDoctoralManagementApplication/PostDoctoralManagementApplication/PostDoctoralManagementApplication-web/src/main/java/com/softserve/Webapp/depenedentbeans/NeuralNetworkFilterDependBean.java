/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.NeuralNetwork;
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
@Named(value = "neuralNetworkFilterDependBean")
@Dependent
public class NeuralNetworkFilterDependBean implements Serializable {
    private String term;
    private List<NeuralNetwork> originalList;
    private List<NeuralNetwork> filteredList;
    /**
     * Creates a new instance of NeuralNetworkFilterDependBean
     */
    public NeuralNetworkFilterDependBean() {
    }
    
    public void init(List<NeuralNetwork> neuralNetworks)
    {
        filteredList = new ArrayList<NeuralNetwork>();
        if(neuralNetworks == null)
        {
            originalList = new ArrayList<NeuralNetwork>();
        }
        else
        {
            originalList = neuralNetworks;
            filteredList.addAll(neuralNetworks);
        }
        
        
    }

    public List<NeuralNetwork> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<NeuralNetwork> filteredList) {
        this.filteredList = filteredList;
    }

    public List<NeuralNetwork> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(List<NeuralNetwork> originalList) {
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
        for(NeuralNetwork neuralNetwork : originalList)
        {
            if(neuralNetwork.getName().contains(term) || neuralNetwork.getNeuralnetworkID().toString().contains(term) || neuralNetwork.getType().contains(term) )
            {
                filteredList.add(neuralNetwork);
            }            
        }
    }
}
