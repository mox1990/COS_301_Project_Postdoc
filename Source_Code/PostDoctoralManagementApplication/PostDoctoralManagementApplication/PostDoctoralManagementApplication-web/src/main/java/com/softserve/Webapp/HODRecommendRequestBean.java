/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import com.softserve.DBEntities.RecommendationReport;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "hodRecommendRequestBean")
@RequestScoped
public class HODRecommendRequestBean {
    
    @Inject
    private HODServicesBean hODServicesBean;
    
    private RecommendationReport recommendationReport = null;
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODRecommendRequestBean() 
    {
        recommendationReport = new RecommendationReport();
    }

    public RecommendationReport getRecommendationReport() {
        return recommendationReport;
    }

    public void setRecommendationReport(RecommendationReport recommendationReport) {
        this.recommendationReport = recommendationReport;
    }
    
    public void preformRecommendRequest()
    {
        hODServicesBean.recommendCurrentlySelectedApplication(recommendationReport);
    }
    
}
