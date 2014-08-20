/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.Webapp.depenedentbeans.FundingReportCreationDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.DRISApprovalServiceLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "drisApproveFundingBean")
@ConversationScoped 
public class DRISApproveFundingBean implements Serializable{

    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private FundingReportCreationDependBean fundingReportCreationDependBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    
    @Inject
    private Conversation conversation;
    
    @EJB
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
    
    private UIComponent errorContainer;
    
    
    
    private String applicantMessage;
    private Notification cscMessage;
    private Notification financeMessage;
    private Date startDate;
    private Date endDate;
    
    private int noOfYears;
    
    /**
     * Creates a new instance of DRISApproveFundingBean
     */
    public DRISApproveFundingBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        fundingReportCreationDependBean.init(null);
        applicantMessage = "";
        cscMessage = new Notification();
        financeMessage = new Notification();
        
        cscMessage.setReciever(new Person());
        financeMessage.setReciever(new Person());
    }            
            
    public UIComponent getErrorContainer() {
        return errorContainer;
    }

    public void setErrorContainer(UIComponent errorContainer) {
        this.errorContainer = errorContainer;
    }

    public FundingReportCreationDependBean getFundingReportCreationDependBean() {
        return fundingReportCreationDependBean;
    }

    public void setFundingReportCreationDependBean(FundingReportCreationDependBean fundingReportCreationDependBean) {
        this.fundingReportCreationDependBean = fundingReportCreationDependBean;
    }

    public Notification getCscMessage() {
        return cscMessage;
    }

    public void setCscMessage(Notification cscMessage) {
        this.cscMessage = cscMessage;
    }

    public Notification getFinanceMessage() {
        return financeMessage;
    }

    public void setFinanceMessage(Notification financeMessage) {
        this.financeMessage = financeMessage;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
                    
    public Application getSelectedApplication()
    {
        return sessionManagerBean.getObjectFromSessionStorage("APPLICATION", Application.class);
    }
    
    public void onYearSlideEnd()
    {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(startDate);
        gregorianCalendar.add(GregorianCalendar.YEAR, noOfYears);
        System.out.println("Number of years " + noOfYears);
        endDate = gregorianCalendar.getTime();
    }
    
    public String preformFundingApprovalRequest()
    {
        try
        {
            Application application = getSelectedApplication();
            application.setStartDate(startDate);
            onYearSlideEnd();
            application.setEndDate(endDate);
            dRISApprovalServiceLocal.approveFunding(sessionManagerBean.getSession(), application, fundingReportCreationDependBean.getFundingReport(),applicantMessage,cscMessage,financeMessage);
            return navigationManagerBean.goToDRISApprovalServiceApplicationSelectionView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.handleException(errorContainer, ex);
            return "";
        }
    }
    
    
}
