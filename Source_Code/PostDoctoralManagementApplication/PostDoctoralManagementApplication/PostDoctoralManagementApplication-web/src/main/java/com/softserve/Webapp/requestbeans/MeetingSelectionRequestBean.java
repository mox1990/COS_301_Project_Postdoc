/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.MeetingManagementServiceLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingSelectionRequestBean")
@RequestScoped
public class MeetingSelectionRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private MeetingManagementServiceLocal  meetingManagementServiceLocal;
    
    /**
     * Creates a new instance of MeetingSelectionRequestBean
     */
    public MeetingSelectionRequestBean() {
    }
    
    public List<CommitteeMeeting> getActiveMeetings()
    {
        try 
        {
            return meetingManagementServiceLocal.getAllActiveMeetings(sessionManagerBean.getSession());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<CommitteeMeeting>();
        }
    }
    
    public List<CommitteeMeeting> getStillToBeHeldMeetings()
    {
        try 
        {
            return meetingManagementServiceLocal.getAllStillToBeHeldMeetings(sessionManagerBean.getSession());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<CommitteeMeeting>();
        }
    }
    
    public List<CommitteeMeeting> getConcludedMeetings()
    {
        try 
        {
            return meetingManagementServiceLocal.getAllConcludedMeetings(sessionManagerBean.getSession());
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<CommitteeMeeting>();
        }
    }
    
    public void selectMeeting(CommitteeMeeting meeting)
    {
        sessionManagerBean.clearSessionStorage();
        sessionManagerBean.addObjectToSessionStorage("MEETING", meeting);
    }
    
    public String viewMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
        return navigationManagerBean.goToMeetingManagementServiceMeetingViewerView();
    }
    
    public String editMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
        return navigationManagerBean.goToMeetingManagementServiceMeetingEditorView();
    }
    
    public void cancelMeeting(CommitteeMeeting meeting)
    {
        try
        {
            meetingManagementServiceLocal.cancelMeeting(sessionManagerBean.getSession(), meeting);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public void openMeeting(CommitteeMeeting meeting)
    {
        try
        {
            meetingManagementServiceLocal.startMeeting(sessionManagerBean.getSession(), meeting);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public void closeMeeting(CommitteeMeeting meeting)
    {
        try
        {
            meetingManagementServiceLocal.endMeeting(sessionManagerBean.getSession(), meeting);
        }
        catch (Exception ex)
        {
            ExceptionUtil.logException(MeetingSelectionRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        }
    }
    
    public String commentOnMeeting(CommitteeMeeting meeting)
    {
        selectMeeting(meeting);
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
}
