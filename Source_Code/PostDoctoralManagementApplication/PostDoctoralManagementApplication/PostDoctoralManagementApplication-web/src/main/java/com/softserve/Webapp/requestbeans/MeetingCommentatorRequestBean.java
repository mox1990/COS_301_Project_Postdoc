/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.MeetingManagementServiceLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "meetingCommentatorRequestBean")
@RequestScoped
public class MeetingCommentatorRequestBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    private MinuteComment minuteComment;
    
    @EJB
    private MeetingManagementServiceLocal meetingManagementServiceLocal;
    /**
     * Creates a new instance of MeetingCommentatorRequestBean
     */
    public MeetingCommentatorRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        minuteComment = new MinuteComment();
    }

    public MinuteComment getMinuteComment() {
        return minuteComment;
    }

    public void setMinuteComment(MinuteComment minuteComment) {
        this.minuteComment = minuteComment;
    }
    
    public CommitteeMeeting getSelectedCommitteeMeeting()
    {
        return sessionManagerBean.getObjectFromSessionStorage("MEETING", CommitteeMeeting.class);
    }
    
    public void preformAddMeetingCommentRequest()
    {
        try
        {            
            minuteComment.setMeeting(getSelectedCommitteeMeeting());
            meetingManagementServiceLocal.addMinuteComment(sessionManagerBean.getSession(), minuteComment);
            sessionManagerBean.updateObjectInSessionStorageAt("MEETING", minuteComment.getMeeting());
        }
        catch(Exception exception)
        {
            ExceptionUtil.logException(MeetingCommentatorRequestBean.class, exception);
            ExceptionUtil.handleException(null, exception);
        }
                
    }
}
