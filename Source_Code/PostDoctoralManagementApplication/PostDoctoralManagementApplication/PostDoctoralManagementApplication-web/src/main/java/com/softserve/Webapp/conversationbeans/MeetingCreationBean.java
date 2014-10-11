/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.applicationservices.DRISApprovalServiceLocal;
import com.softserve.ejb.applicationservices.MeetingManagementServiceLocal;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "meetingCreationBean")
@ConversationScoped
public class MeetingCreationBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private MeetingManagementServiceLocal  meetingManagementServiceLocal;
    @EJB 
    private DRISApprovalServiceLocal dRISApprovalServiceLocal;
    
    private List<Application> selectedApplicationList;
    private List<Application> appliationSelectionList;
    private List<Person> selectedAttendeesList;
    private List<Person> attendeesSelectionList;
    
    private CommitteeMeeting committeeMeeting;
    
    /**
     * Creates a new instance of MeetingCreationBean
     */
    public MeetingCreationBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        
        try 
        {
            selectedApplicationList = new ArrayList<Application>();
            selectedAttendeesList = new ArrayList<Person>();
        
            committeeMeeting = new CommitteeMeeting();
        
        
            Session session = sessionManagerBean.getSession();
            appliationSelectionList = dRISApprovalServiceLocal.loadPendingEligibleApplications(session, 0, Integer.MAX_VALUE);
            attendeesSelectionList = meetingManagementServiceLocal.getAllPostDocCommitteeMembers(session);
        } 
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            appliationSelectionList = new ArrayList<Application>();
            attendeesSelectionList = new ArrayList<Person>();
            navigationManagerBean.callFacesNavigator(navigationManagerBean.goToMeetingManagementServiceHomeView());
        }
        
    }

    public List<Application> getAppliationSelectionList() {
        return appliationSelectionList;
    }

    public void setAppliationSelectionList(List<Application> appliationSelectionList) {
        this.appliationSelectionList = appliationSelectionList;
    }

    public List<Person> getAttendeesSelectionList() {
        return attendeesSelectionList;
    }

    public void setAttendeesSelectionList(List<Person> attendeesSelectionList) {
        this.attendeesSelectionList = attendeesSelectionList;
    }

    public List<Application> getSelectedApplicationList() {
        return selectedApplicationList;
    }

    public void setSelectedApplicationList(List<Application> selectedApplicationList) {
        this.selectedApplicationList = selectedApplicationList;
    }

    public List<Person> getSelectedAttendeesList() {
        return selectedAttendeesList;
    }

    public void setSelectedAttendeesList(List<Person> selectedAttendeesList) {
        this.selectedAttendeesList = selectedAttendeesList;
    }

    public CommitteeMeeting getCommitteeMeeting() {
        return committeeMeeting;
    }

    public void setCommitteeMeeting(CommitteeMeeting committeeMeeting) {
        this.committeeMeeting = committeeMeeting;
    }
    
    public String performMeetingCreationRequest()
    {
        try
        {
            committeeMeeting.setApplicationList(selectedApplicationList);
            committeeMeeting.setPersonList(selectedAttendeesList);
            meetingManagementServiceLocal.createMeeting(sessionManagerBean.getSession(), committeeMeeting);
            
            conversationManagerBean.deregisterConversation(conversation);
            return navigationManagerBean.goToMeetingManagementServiceHomeView();
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(MeetingCreationBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return "";
        }
    }
    
}
