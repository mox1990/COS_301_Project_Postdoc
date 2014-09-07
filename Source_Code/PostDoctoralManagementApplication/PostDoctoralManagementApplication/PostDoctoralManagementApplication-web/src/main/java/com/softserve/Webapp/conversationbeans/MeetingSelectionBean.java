/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Webapp.depenedentbeans.MeetingFilterDependBean;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.MeetingManagementServiceLocal;
import com.softserve.system.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingSelectionBean")
@ConversationScoped
public class MeetingSelectionBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private MeetingFilterDependBean meetingFilterDependBeanActive;
    @Inject
    private MeetingFilterDependBean meetingFilterDependBeanConcluded;
    @Inject
    private MeetingFilterDependBean meetingFilterDependBeanStillToBeHeld;
    @Inject
    private Conversation conversation;
    
    @EJB
    private MeetingManagementServiceLocal  meetingManagementServiceLocal;
    
    /**
     * Creates a new instance of MeetingSelectionRequestBean
     */
    public MeetingSelectionBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
        try
        {
            if(isUserDRIS())
            {            
                Session session = sessionManagerBean.getSession();
                meetingFilterDependBeanStillToBeHeld.init(meetingManagementServiceLocal.getAllStillToBeHeldMeetings(session));
                meetingFilterDependBeanConcluded.init(meetingManagementServiceLocal.getAllConcludedMeetings(session));
                
                meetingFilterDependBeanActive.init(meetingManagementServiceLocal.getAllActiveMeetings(session));
            }
            else
            {
                meetingFilterDependBeanActive.init(meetingManagementServiceLocal.getAllActiveMeetingsForWhichUserIsToAttend(sessionManagerBean.getSession()));
            }
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }

    public MeetingFilterDependBean getMeetingFilterDependBeanActive() {
        return meetingFilterDependBeanActive;
    }

    public MeetingFilterDependBean getMeetingFilterDependBeanConcluded() {
        return meetingFilterDependBeanConcluded;
    }

    public MeetingFilterDependBean getMeetingFilterDependBeanStillToBeHeld() {
        return meetingFilterDependBeanStillToBeHeld;
    }
            
    public void selectMeeting(CommitteeMeeting meeting)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("MEETING", meeting);
    }
    
    public String viewMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToMeetingManagementServiceMeetingViewerView();
    }
    
    public String editMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToMeetingManagementServiceMeetingEditorView();
    }
    
    public void cancelMeeting(CommitteeMeeting meeting)
    {
        try
        {   
            meetingManagementServiceLocal.cancelMeeting(sessionManagerBean.getSession(), meeting);
            conversationManagerBean.deregisterConversation(conversation);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public void openMeeting(CommitteeMeeting meeting)
    {
        try
        {
            meetingManagementServiceLocal.startMeeting(sessionManagerBean.getSession(), meeting);
            conversationManagerBean.deregisterConversation(conversation);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public void closeMeeting(CommitteeMeeting meeting)
    {
        try
        {
            meetingManagementServiceLocal.endMeeting(sessionManagerBean.getSession(), meeting);
            conversationManagerBean.deregisterConversation(conversation);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public String commentOnMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
        conversationManagerBean.deregisterConversation(conversation);
        return navigationManagerBean.goToMeetingManagementServiceMeetingCommentatorView();
    }
    
    public boolean isUserDRIS()
    {
        ArrayList<SecurityRole> securityRoles = new ArrayList<SecurityRole>();
        securityRoles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        try 
        {
            return sessionManagerBean.getSession().doesUserHaveAnyOfTheseSecurityRole(securityRoles);
        } 
        catch (Exception ex) 
        {
            return false;
        } 
    }
    
    public boolean isAttendeeOfMeeting(CommitteeMeeting meeting)
    {
        try 
        {
            return meeting.getPersonList().contains(sessionManagerBean.getSession().getUser());
        } 
        catch (Exception ex) 
        {
            return false;
        }
    }
    
    public boolean isOrganiserOfMeeting(CommitteeMeeting meeting)
    {
        try 
        {
            return meeting.getOrganiser().equals(sessionManagerBean.getSession().getUser());
        } 
        catch (Exception ex) 
        {
            return false;
        }
    }
}
