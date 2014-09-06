/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ResearchFellowInformation;
import com.softserve.Webapp.depenedentbeans.FundingReportCreationDependBean;
import com.softserve.Webapp.depenedentbeans.LocationFinderDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.DRISApprovalServiceLocal;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "dirsApplicationFundingEditBean")
@ConversationScoped
public class DRISApplicationFundingEditBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private FundingReportCreationDependBean fundingReportCreationDependBean;
    @Inject
    private LocationFinderDependBean locationFinderDependBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    
    @Inject
    private Conversation conversation;
    
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
    
    private ResearchFellowInformation researchFellowInformation;
    private Date startDate;
    private Date endDate;
    
    private int noOfYears;
    /**
     * Creates a new instance of DRISFundingReportEditBean
     */
    public DRISApplicationFundingEditBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        Application openApplication = getSelectedApplication();
        
        fundingReportCreationDependBean.init(openApplication.getFundingReport());
        
        researchFellowInformation = openApplication.getFellow().getResearchFellowInformation();
        
        endDate = openApplication.getEndDate();
        startDate = openApplication.getStartDate();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(endDate.getTime() - startDate.getTime());
        noOfYears  = calendar.get(GregorianCalendar.YEAR);
    } 
    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }

    public FundingReportCreationDependBean getFundingReportCreationDependBean() {
        return fundingReportCreationDependBean;
    }

    public void setFundingReportCreationDependBean(FundingReportCreationDependBean fundingReportCreationDependBean) {
        this.fundingReportCreationDependBean = fundingReportCreationDependBean;
    }

    public LocationFinderDependBean getLocationFinderDependBean() {
        return locationFinderDependBean;
    }

    public void setLocationFinderDependBean(LocationFinderDependBean locationFinderDependBean) {
        this.locationFinderDependBean = locationFinderDependBean;
    }

    public ResearchFellowInformation getResearchFellowInformation() {
        return researchFellowInformation;
    }

    public void setResearchFellowInformation(ResearchFellowInformation researchFellowInformation) {
        this.researchFellowInformation = researchFellowInformation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNoOfYears() {
        return noOfYears;
    }

    public void setNoOfYears(int noOfYears) {
        this.noOfYears = noOfYears;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public void onYearSlideEnd()
    {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(startDate);
        gregorianCalendar.add(GregorianCalendar.YEAR, noOfYears);
        System.out.println("Number of years " + noOfYears);
        endDate = gregorianCalendar.getTime();
    }
    
    public String preformFundingEditRequest()
    {
        try
        {
            Application application = getSelectedApplication();
            application.setStartDate(startDate);
            onYearSlideEnd();
            application.setEndDate(endDate);
            researchFellowInformation.setDepartment(locationFinderDependBean.getActualDepartmentEntity(researchFellowInformation.getDepartment().getDepartmentID()));
            researchFellowInformation.setSystemAssignedID(application.getFellow().getSystemID());
            researchFellowInformation.setPerson(application.getFellow());
            
            application.getFellow().setResearchFellowInformation(researchFellowInformation);
            application.setFundingReport(fundingReportCreationDependBean.getFundingReport());            
            
            dRISApprovalServiceLocal.updateFundingInformation(sessionManagerBean.getSession(), application);
            return navigationManagerBean.goToDRISApprovalServiceApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(DRISApproveFundingBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
}
