/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import com.softserve.DBEntities.FundingCost;
import com.softserve.DBEntities.FundingReport;
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
    
    public void addFundingCostToList()
    {
        fundingReport.getFundingCostList().add(currentFundingCost);
        currentFundingCost = new FundingCost();
        MessageUtil.CreateGlobalFacesMessage("Funding cost added!","The funding cost has been added to the list.", FacesMessage.SEVERITY_INFO);
    }
    
}
