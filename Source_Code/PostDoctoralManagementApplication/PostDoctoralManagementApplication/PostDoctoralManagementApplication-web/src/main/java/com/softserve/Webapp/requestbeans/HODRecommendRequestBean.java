/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import auto.softserve.XMLEntities.HOD.RecommendationReportContent;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.HODRecommendationServices;
import com.softserve.ejb.HODRecommendationServicesLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
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
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private HODRecommendationServicesLocal hodRecommendationServicesLocal;
    
    private UIComponent errorContainer; 
    
    private RecommendationReport recommendationReport = null;
    private RecommendationReportContent recommendationReportContent = null;
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODRecommendRequestBean() 
    {        
    }
    
    @PostConstruct
    public void init()
    {
        recommendationReport = new RecommendationReport();
        recommendationReportContent = new RecommendationReportContent();
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStroage(0, Application.class);
    }

    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }

    public RecommendationReport getRecommendationReport() {
        return recommendationReport;
    }

    public void setRecommendationReport(RecommendationReport recommendationReport) {
        this.recommendationReport = recommendationReport;
    }

    public RecommendationReportContent getRecommendationReportContent() {
        return recommendationReportContent;
    }

    public void setRecommendationReportContent(RecommendationReportContent recommendationReportContent) {
        this.recommendationReportContent = recommendationReportContent;
    }
            
    public String preformRecommendRequest()
    {
        try
        {
            recommendationReport.setContentXMLEntity(recommendationReportContent);
            hodRecommendationServicesLocal.approveApplication(sessionManagerBean.getSession(), getSelectedApplication(), recommendationReport);
            return navigationManagerBean.goToHODApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
}
