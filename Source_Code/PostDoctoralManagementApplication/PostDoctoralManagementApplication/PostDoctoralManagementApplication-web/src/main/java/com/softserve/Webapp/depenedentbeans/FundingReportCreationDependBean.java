/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.Webapp.util.MessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "fundingReportCreationDependBean")
@Dependent
public class FundingReportCreationDependBean implements Serializable{

    /**
     * Creates a new instance of FundingReportCreationDependBean
     */
    
    private ArrayList<FundingCost> selectedFundingCosts;
    private FundingCost currentFundingCost;
    private FundingReport fundingReport;

    public FundingReportCreationDependBean() {
    }
    
    public void init(FundingReport fundingreport)
    {
        currentFundingCost = new FundingCost();
        if(fundingreport == null)
        {
            fundingReport = new FundingReport();
            
        }
        else
        {
            fundingReport = fundingreport;
            
        }
        
        if(fundingReport.getFundingCostList() == null)
        {
            fundingReport.setFundingCostList(new ArrayList<FundingCost>());
        }
        
        selectedFundingCosts = new ArrayList<FundingCost>();
    }

    public FundingCost getCurrentFundingCost() {
        return currentFundingCost;
    }

    public void setCurrentFundingCost(FundingCost currentFundingCost) {
        this.currentFundingCost = currentFundingCost;
    }

    public FundingReport getFundingReport() {
        return fundingReport;
    }

    public void setFundingReport(FundingReport fundingReport) {
        this.fundingReport = fundingReport;
    }

    public ArrayList<FundingCost> getSelectedFundingCosts() {
        return selectedFundingCosts;
    }

    public void setSelectedFundingCosts(ArrayList<FundingCost> selectedFundingCosts) {
        this.selectedFundingCosts = selectedFundingCosts;
    }
    
       
    public void addFundingCostToList()
    {
        System.out.println(fundingReport.getFundingCostList().size());
         System.out.println(fundingReport.getFundingCostList().toString());
        fundingReport.getFundingCostList().add(currentFundingCost);
        System.out.println(fundingReport.getFundingCostList().toString());
         System.out.println(fundingReport.getFundingCostList().size());
        currentFundingCost = new FundingCost();
        MessageUtil.CreateGlobalFacesMessage("Funding cost added!","The funding cost has been added to the list.", FacesMessage.SEVERITY_INFO);
    }
    
    public void removeFundingCostFromList()
    {
        if(selectedFundingCosts.size() > 0)
        {
            ArrayList<FundingCost> newFundingCosts = new ArrayList<FundingCost>();
            
            for(FundingCost fundingCost : fundingReport.getFundingCostList())
            {
                String removeValue = fundingCost.getProvider()+ " " + fundingCost.getType()+ " " + fundingCost.getAmount();
                
                boolean found = false;
                
                for(FundingCost fundingCost1 : selectedFundingCosts)
                {                    
                    String value = fundingCost1.getProvider()+ " " + fundingCost1.getType()+ " " + fundingCost1.getAmount();
                    if(removeValue.equals(value))
                    {
                       found = true;
                    }
                }
                
                if(!found)
                {
                    newFundingCosts.add(fundingCost);
                }
            }
            
            fundingReport.setFundingCostList(newFundingCosts);
            
            selectedFundingCosts = new ArrayList<FundingCost>();
            MessageUtil.CreateGlobalFacesMessage("Funding costs removed!","The selected funding costs have been removed from the list.", FacesMessage.SEVERITY_INFO);
        }
        else
        {
            MessageUtil.CreateGlobalFacesMessage("No funding costs selected!", "You have to select funding costs which need to be removed from the list!", FacesMessage.SEVERITY_WARN);
        }
    }
        
}
