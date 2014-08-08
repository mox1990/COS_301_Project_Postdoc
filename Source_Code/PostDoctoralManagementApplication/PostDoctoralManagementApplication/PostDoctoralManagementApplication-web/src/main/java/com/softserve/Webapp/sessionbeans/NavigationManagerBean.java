/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import com.softserve.Webapp.util.BreadCrumb;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "navigationManagerBean")
@SessionScoped
public class NavigationManagerBean implements Serializable {
    
    @Inject
    private ConversationManagerBean conversationManagerBean;
    
    private ArrayList<BreadCrumb> breadCrumbs;
    
    /**
     * Creates a new instance of navigationBean
     */
    public NavigationManagerBean() 
    {
        breadCrumbs = new ArrayList<BreadCrumb>();
    }
    
    @PostConstruct
    public void init()
    {
        goToPortalView();
    }
    
    public List<BreadCrumb> getBreadCrumbs() {
        return breadCrumbs;
    }
    
    public BreadCrumb getLatestBreadCrumb()
    {
        if(breadCrumbs.isEmpty())
        {
            return new BreadCrumb("");
        }
        else
        {
            return breadCrumbs.get(breadCrumbs.size() - 1);
        }
    }
    
    private void addToBreadCrumbs(BreadCrumb breadCrumb)
    {
        if(!breadCrumbs.contains(breadCrumb))
        {
            breadCrumbs.add(breadCrumb);
        }
    }
    
    public void clearBreadCrumbsTo(int index)
    {
        conversationManagerBean.deregisterAllConverstations();
        
        while(breadCrumbs.size() > 0 && breadCrumbs.size() - 1 > index)
        {            
            breadCrumbs.remove(breadCrumbs.size() - 1);
        }
        
    }
    
    public String goToBreadCrumb(int index)
    {
        BreadCrumb breadCrumb = breadCrumbs.get(index);
        clearBreadCrumbsTo(index);
        
        return goToLink(breadCrumb);
    }
    
    public String goToPreviousBreadCrumb()
    {
        return goToBreadCrumb(breadCrumbs.size() - 2);
    }
    
    public int getIndexOfBreadCrumbWithPath(String path)
    {
        BreadCrumb breadCrumb = new BreadCrumb(path);
        breadCrumb.stripPath(FacesContext.getCurrentInstance());
        System.out.print("=======Link after strip " + breadCrumb.getPageLinkFromContext());
        return breadCrumbs.indexOf(breadCrumb);
    }
    
    private String goToLink(BreadCrumb breadCrumb)
    {
        if(breadCrumbs.contains(breadCrumb) && !getLatestBreadCrumb().equals(breadCrumb))
        {
            return goToBreadCrumb(breadCrumbs.indexOf(breadCrumb));
        }
        else
        {
            breadCrumb.setQueryString("faces-redirect=true");
            
            return breadCrumb.getURL();
        }
    }
    
    public String CheckPath(String path)
    {
        System.out.println("================ Path " + path);
        int i = getIndexOfBreadCrumbWithPath(path);
        
        System.out.println("================ P" + i);
        System.out.println("================ L" + getLatestBreadCrumb().getPageLinkFromContext());
        if(i < 0)
        {
            System.out.println("O1");            
            return goToLink(getLatestBreadCrumb());
        }
        else if(i != breadCrumbs.size() - 1)
        {
            System.out.println("O2");
            return goToBreadCrumb(i);
        }
        else
        {
            System.out.println("O3");
            return "";
        }
        
    }
    
    public void callFacesNavigator(String path)
    {
        if(!path.equals(""))
        {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, path);
        }
    }
    public String goToPortalView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("index", "Portal Login");
        clearBreadCrumbsTo(-1);
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountCreationForProspectiveFellowView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagement_ProspectiveFellowCreateUser", "Prospective Fellow account creation view");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToWelcomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("welcome", "Home");
        clearBreadCrumbsTo(0);
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationServices_Home", "Application services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToReportServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ReportServices_Home", "Report services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDataImportAndExportServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DataImportAndExportServices_Home", "Data import and export services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_Home", "User account management services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToArchivalServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ArchivalServices_Home", "Archival services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToAduitTrailServicesHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("AuditTrailServices_Home", "Audit trail services home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODApplicationViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationViewer", "Application viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODDeclineView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationDecline", "Decline application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODAmmendView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationAmmend", "Request application ammendment");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODRecommendView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationRecommend", "Recommend application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToHODApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("HODRecommendationService_ApplicationSelection", "Application selection view");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementAccountsViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_AccountsViewer", "Accounts list");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementlAccountViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_AccountViewer", "Account viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementPersonalAccountViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_PersonalAccountViewer", "Personal account viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementGeneralAccountCreationView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_GeneralAccountCreationView", "User account creation");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    
    public String goToDeanApplicationViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DeanEndorsementService_ApplicationViewer", "Application viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDeanDeclineView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("DeanEndorsementService_DeclineApplication", "Decline application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDeanEndorseView()
    {        
        BreadCrumb breadCrumb = new BreadCrumb("DeanEndorsementService_EndorseApplication", "Endorse application");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDeanApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DeanEndorsementService_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToNewApplicationWizardView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("newApplicationService_Wizard", "New application wizard");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationProgressViewerApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationProgressViewerService_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationProgressViewerApplicationProgressView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationProgressViewerService_ApplicationProgress", "Application progress viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToRefereeReportServiceApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("RefereeReportService_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToRefereeReportServiceReportCreationView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("RefereeReportService_ReportCreation", "Referral report creation");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDRISApprovalServiceApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DRISApprovalService_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDRISApprovalServiceApplicationViewer()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DRISApprovalService_ApplicationViewer", "Application viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDRISApprovalServiceApproveFundingSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DRISApprovalService_FundingApproval", "Funding approval");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToDRISApprovalServiceDeclineFundingSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("DRISApprovalService_DeclineFunding", "Decline Funding");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToUserAccountManagementServicesOnDemandUserActivationView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("UserAccountManagementServices_OnDemandUserActivation", "On demand user activation");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToGrantHolderFinalisationServiceApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("GrantHolderFinalisationService_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToGrantHolderFinalisationServiceFinalisationWizardView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("GrantHolderFinalisationService_FinalisationWizard", "Finalisation wizard");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceHomeView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementServices_Home", "Meeting management home");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceMeetingCreationView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementServices_MeetingCreation", "Meeting creation menu");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceMeetingCommentatorView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementServices_MeetingCommentator", "Meeting commentator");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceMeetingSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementServices_MeetingSelection", "Meeting selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceMeetingViewerView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementService_MeetingViewer", "Meeting viewer");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToMeetingManagementServiceMeetingEditorView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("MeetingManagementServices_MeetingEditor", "Meeting editor");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToProgressReportManagementServiceApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ProgressReportManagement_ApplicationSelection", "Application selection");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToProgressReportManagementServiceReportCreationView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ProgressReportManagement_ReportCreation", "Report creation");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationRenewalServiceApplicationSelectionView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationRenewalService_ApplicationSelection", "Report creation");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToApplicationRenewalServiceWizardView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("ApplicationRenewalService_Wizard", "Application renwal wizard");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
    
    public String goToNotificationServiceNotificationManagementView()
    {
        BreadCrumb breadCrumb = new BreadCrumb("NotificationService_NotificationManagement", "Notification manager");
        addToBreadCrumbs(breadCrumb);
        
        return goToLink(breadCrumb);
    }
}
