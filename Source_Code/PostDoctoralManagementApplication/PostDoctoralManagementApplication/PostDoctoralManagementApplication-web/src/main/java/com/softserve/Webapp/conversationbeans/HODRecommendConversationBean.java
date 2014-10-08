/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import auto.softserve.XMLEntities.HOD.RecommendationReportContent;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.RecommendationReport;
import com.softserve.Webapp.depenedentbeans.ApplicationReviewRequestCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.applicationservices.HODRecommendationServices;
import com.softserve.ejb.applicationservices.HODRecommendationServicesLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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
@ConversationScoped
public class HODRecommendConversationBean implements Serializable{
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    @Inject 
    private ApplicationReviewRequestCreationDependBean applicationReviewRequestCreationDependBean;
    
    @EJB
    private HODRecommendationServicesLocal hodRecommendationServicesLocal;
    
    private RecommendationReport recommendationReport = null;
    private RecommendationReportContent recommendationReportContent = null;
    
    /**
     * Creates a new instance of HODRecommendBean
     */
    public HODRecommendConversationBean() 
    {        
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        try
        {
            if(getSelectedApplication() == null)
            {
                throw new Exception("No application selected");
            }
            
            recommendationReport = new RecommendationReport();
            recommendationReportContent = new RecommendationReportContent();
        
            applicationReviewRequestCreationDependBean.init(hodRecommendationServicesLocal.getDeansOfApplication(sessionManagerBean.getSession(), getSelectedApplication()));
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(HODRecommendConversationBean.class, ex);          
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToHODApplicationViewer());
        }
    }
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
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

    public ApplicationReviewRequestCreationDependBean getApplicationReviewRequestCreationDependBean() {
        return applicationReviewRequestCreationDependBean;
    }

    public void setApplicationReviewRequestCreationDependBean(ApplicationReviewRequestCreationDependBean applicationReviewRequestCreationDependBean) {
        this.applicationReviewRequestCreationDependBean = applicationReviewRequestCreationDependBean;
    }
            
    public String preformRecommendRequest()
    {
        try
        {
            recommendationReport.setContentXMLEntity(recommendationReportContent);
            Application application = getSelectedApplication();
            hodRecommendationServicesLocal.requestSpecificDeanToReview(sessionManagerBean.getSession(), application, applicationReviewRequestCreationDependBean.getPerson());
            hodRecommendationServicesLocal.recommendApplication(sessionManagerBean.getSession(), application, recommendationReport);
            return navigationManagerBean.goToHODApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(HODRecommendConversationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
