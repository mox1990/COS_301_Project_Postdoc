/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.Webapp.depenedentbeans.ApplicationCreationDependBean;
import com.softserve.Webapp.depenedentbeans.FundingReportCreationDependBean;
import com.softserve.Webapp.depenedentbeans.LocationFinderDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.ejb.nonapplicationservices.ApplicationImportServicesLocal;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.ResearchFellowInformation;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "applicationImportBean")
@ConversationScoped
public class ApplicationImportBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private ApplicationImportServicesLocal applicationImportServicesLocal;
    
    
    private int wizardActiveTab;
    private final int MAX_TAB_INDEX = 5;
    
    private ResearchFellowInformation researchFellowInformation;;
    private Date startDate;
    private Date endDate;
    
    private int noOfYears;
    
    @Inject
    private ApplicationCreationDependBean ApplicationCreationDependBean;
    @Inject
    private FundingReportCreationDependBean fundingReportCreationDependBean;
    @Inject
    private LocationFinderDependBean locationFinderDependBean;
    
    /**
     * Creates a new instance of ApplicationImportBean
     */
    public ApplicationImportBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try 
        {  
            Session session = null;
            
            session = sessionManagerBean.getSession();
            ApplicationCreationDependBean.init(null);
        
            wizardActiveTab = 0;
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToApplicationServicesHomeView());
        }
    }
   
    public int getWizardActiveTab() {
        return wizardActiveTab;
    }

    public void setWizardActiveTab(int wizardActiveTab) {
        this.wizardActiveTab = wizardActiveTab;
    }
    
    public void goBack()
    {
        if(wizardActiveTab > 0 && wizardActiveTab <=  MAX_TAB_INDEX)
        {
            wizardActiveTab--;
        }
    }

    public ApplicationCreationDependBean getApplicationCreationDependBean() {
        return ApplicationCreationDependBean;
    }

    public FundingReportCreationDependBean getFundingReportCreationDependBean() {
        return fundingReportCreationDependBean;
    }

    public LocationFinderDependBean getLocationFinderDependBean() {
        return locationFinderDependBean;
    }

    public int getNoOfYears() {
        return noOfYears;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setNoOfYears(int noOfYears) {
        this.noOfYears = noOfYears;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ResearchFellowInformation getResearchFellowInformation() {
        return researchFellowInformation;
    }

    public void setResearchFellowInformation(ResearchFellowInformation researchFellowInformation) {
        this.researchFellowInformation = researchFellowInformation;
    }
    
    public void onYearSlideEnd()
    {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(startDate);
        gregorianCalendar.add(GregorianCalendar.YEAR, noOfYears);
        System.out.println("Number of years " + noOfYears);
        endDate = gregorianCalendar.getTime();
    }
    
    public void next()
    {
        if(wizardActiveTab > -1 && wizardActiveTab <=  MAX_TAB_INDEX)
        {
            wizardActiveTab++;
        }
    }
    
    public String preformApplicationImport()
    {
        try 
        {
            Application application = getApplicationCreationDependBean().getCombinedApplication();
            
            FundingReport fundingReport = getFundingReportCreationDependBean().getFundingReport();
            application.setStartDate(startDate);
            application.setEndDate(endDate);
            application.setFellow(ApplicationCreationDependBean.getFellow());
            application.getFellow().setResearchFellowInformation(researchFellowInformation);
            
            applicationImportServicesLocal.importApplication(sessionManagerBean.getSession(), application, fundingReport);
            return navigationManagerBean.goToPreviousBreadCrumb();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(NewApplicationCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        } 
    }
    
}
